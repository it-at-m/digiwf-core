<template>
  <div id="top">
    <v-autocomplete
      ref="autocompletion"
      v-model="selectedUsers"
      :aria-required="isRequired()"
      :auto-select-first="!screenreaderMode"
      :class="[isReadonly() ? 'userInputReadonly' : 'userInput']"
      :filter="filterUsers"
      :items="entries()"
      :label="label"
      :loading="isLoading"
      :readonly="isReadonly()"
      :rules="rules ? rules : true"
      :search-input.sync="searchText"
      chips
      hide-no-data
      item-text="username"
      item-value="lhmObjectId"
      multiple
      placeholder="Benutzer suchen..."
      return-object
      small-chips
      v-bind="schema['x-props']"
      @change="change"
    >
      <template #label>
        <span
          :aria-label="label"
          :aria-required="isRequired()"
          tabindex="0"
        >{{ label }}</span>
        <span
          v-if="isRequired()"
          aria-hidden="true"
          style="font-weight: bold; color: red"
        >
          *</span
        >
      </template>
      <template #selection="data">
        <v-chip
          :aria-label="getFullName(data.item) + data.item.ou"
          :input-value="data.selected"
          class="ma-1 pa-4"
          small
          tabindex="0"
          v-bind="data.attrs"
        >
          <v-avatar left>
            <v-img :src="mucatarUrl(data.item.username)">
              <template #placeholder>
                <v-icon>mdi-account</v-icon>
              </template>
            </v-img>
          </v-avatar>
          {{ getFullName(data.item) }} ({{ data.item.ou }})
          <v-icon
            v-if="!readonly"
            :aria-label="getFullName(data.item) + data.item.ou + ' entfernen'"
            class="ml-2"
            size="20"
            @click.stop="removeUser(data.item)">
            mdi-close-circle
          </v-icon>
        </v-chip>
      </template>
      <template #item="data">
        <template>
          <v-list-item-avatar>
            <v-img :src="mucatarUrl(data.item.username)">
              <template #placeholder>
                <v-icon>mdi-account</v-icon>
              </template>
            </v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title>{{ getFullName(data.item) }}</v-list-item-title>
            <v-list-item-subtitle v-html="data.item.ou"/>
          </v-list-item-content>
        </template>
      </template>
    </v-autocomplete>
  </div>
</template>

<style>
/* Hide Expand/Collapse-Icon */
#top .v-autocomplete .v-input__append-inner > div {
  display: none;
}

#top .v-chip__content {
  font-size: 14px;
}
</style>

<script lang="ts">
import {UserTO} from "@muenchen/digiwf-engine-api-internal";
import {computed, defineComponent, PropType, ref, watch} from "vue";

import {callGetUserById, callGetUserByUsername, callSearchUser,} from "../../api/user/userApiCalls";
import {mucatarURL} from "../../constants";
import {checkRequired} from "./validation/required";
import {useAccessibility} from "../../store/modules/accessibility";

export interface OnProperty {
  input: (value: any) => void;
}

export default defineComponent({
  props: {
    valid: {
      type: Boolean,
      required: false,
      default: true,
    },
    hasFocused: {
      type: Boolean,
      required: false,
      default: false,
    },
    value: {
      // lhmObjectIds of all selected users
      type: Array as PropType<string[]>,
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    rules: {
      type: Array, // https://v2.vuetifyjs.com/en/api/v-autocomplete/#props
      required: false,
      default: undefined, // FIXME: correct? (in html code default value is true)
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false,
    },
    on: {
      type: Object as PropType<OnProperty>,
      required: false,
      default: undefined,
    },
    schema: {
      type: Object,
      required: true,
    },
    // @Prop()
    // options: any; FIXME: check if it es necessary, can't find usage right now
    //
    // @Prop()
    // htmlDescription: string | undefined;  FIXME: check if it es necessary, can't find usage right now
    //
  },

  setup: (props: any) => {
    const schemaObj = JSON.parse(JSON.stringify(props.schema));
    const readonly: boolean = schemaObj.readOnly || false;
    const ldapGroups = schemaObj["ldap-groups"];

    /**
     * all users which are already selected with complete user information.
     * each entry of props.value should have an entry in selectedUsers
     */
    const selectedUsers = ref<UserTO[]>([]);

    const searchText = ref("");
    const items = ref<UserTO[]>([]); // search result
    const isLoading = ref(false);
    /**
     * is true when initial loading is running, otherwise false
     */
    const locked = ref(false);
    const errorMessage = ref("");
    const currentSearch = ref("");

    const a11YScreenreaderModeEnabled = useAccessibility().a11YScreenreaderModeEnabled;

    const screenreaderMode = computed(() => a11YScreenreaderModeEnabled());

    const autocompletion = ref();

    watch(searchText, (newValue) => {
      searchUsersBySearchString(newValue);
    });

    const isReadonly = () => readonly || locked.value;

    const entries = (): UserTO[] => items.value.concat(selectedUsers.value);

    const input = (value: string[]) => {
      searchText.value = "";
      items.value = [];
      props.on.input(value);
    };

    const loadUser = (idOrUsername: string) => {
      const isId = idOrUsername.match(/^-?\d+$/);
      locked.value = true;
      (isId
          ? callGetUserById(idOrUsername)
          : callGetUserByUsername(idOrUsername)
      )
        .then((user) => {
          selectedUsers.value = [...selectedUsers.value, user];
          errorMessage.value = "";
        })
        .catch(() => {
          errorMessage.value = "Ein Benutzer konnte nicht geladen werden.";
        })
        .finally(() => {
          locked.value = false;
        });
    };

    const getFullName = (user: UserTO): string =>
      `${user.forename} ${user.surname}`;

    const filterUsers = (item: UserTO, queryText: string): boolean => {
      return getFullName(item).toLowerCase().includes(queryText.toLowerCase());
    };

    const isRequired = () => checkRequired(props.schema);

    const removeUser = (user: UserTO): void => {
      resetInput();
      selectedUsers.value = selectedUsers.value.filter(
        (it) => it.lhmObjectId !== user.lhmObjectId
      );
      change();
      autocompletion.value.focus();
    };

    const mucatarUrl = (uid: string) => mucatarURL(uid);

    const searchUsersBySearchString = (searchString: string) => {
      if (!searchString || searchString.length < 3) return;

      currentSearch.value = searchString.slice(0, 3);

      isLoading.value = true;

      callSearchUser(currentSearch.value, ldapGroups)
        .then((users) => {
          if (currentSearch.value === searchText.value.slice(0, 3)) {
            items.value = users;
          }
          errorMessage.value = "";
        })
        .catch(() => {
          errorMessage.value = "Der Benutzer konnte nicht geladen werden.";
        })
        .finally(() => {
          isLoading.value = false;
        });
    };

    const change = (): void => {
      const selectedLhmObjectIds = selectedUsers.value
        .map((a) => a.lhmObjectId)
        .filter((it): it is string => !!it);
      input(selectedLhmObjectIds);
    };

    const resetInput = (): void => {
      currentSearch.value = "";
      searchText.value = "";
      items.value = [];
    };

    /*
      load initial value
     */

    if (readonly) {
      items.value = selectedUsers.value; // in readonly: show initial values in autocomplete
    }
    if (props.value && props.value.length > 0) {
      locked.value = true;
      Promise.all(props.value.map(loadUser)).finally(() => {
        locked.value = false;
      });
    }

    return {
      autocompletion,
      screenreaderMode,
      resetInput,
      change,
      getFullName,
      filterUsers,
      isRequired,
      isReadonly,
      entries,
      selectedUsers,
      isLoading,
      searchText,
      readonly,
      removeUser,
      mucatarUrl,
    };
  },
});
</script>
