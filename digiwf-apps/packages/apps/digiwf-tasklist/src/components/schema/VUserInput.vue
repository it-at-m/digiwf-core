<template>
  <v-autocomplete
    :aria-required="isRequired()"
    :filter="filterUsers"
    :items="entries()"
    :label="label"
    :loading="isLoading"
    :no-data-text="noDataText"
    :readonly="isReadonly()"
    :rules="rules ? rules : true"
    :search-input.sync="searchText"
    :value="selectedUser"
    auto-select-first
    item-text="lhmObjectId"
    item-value="lhmObjectId"
    placeholder="Benutzer suchen..."
    v-bind="schema['x-props']"
    @input="input"
  >
    <template #label>
      <span>{{ label }}</span>
      <span
        v-if="isRequired()"
        aria-hidden="true"
        style="font-weight: bold; color: red"
      >
        *</span
      >
    </template>
    <template #selection="data">
      {{ getFullName(data.item) }}
    </template>
    <template #item="data">
      <template>
        <v-list-item-avatar class="avatar">
          {{ getNamePrefix(data.item) }}
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>{{ getFullName(data.item) }}</v-list-item-title>
          <v-list-item-subtitle>{{
            castNoAttrAvailable(data.item.email)
          }}</v-list-item-subtitle>
          <v-list-item-subtitle>
            {{ castNoAttrAvailable(data.item.department) }}
            <span class="dot">&#8226;</span>
            {{ castNoAttrAvailable(data.item.ou) }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </template>
    </template>
  </v-autocomplete>
</template>

<style scoped>
.avatar {
  background-color: gray;
  color: white;
}

.dot {
  margin: 0 0.2rem;
  font-size: 1.2rem;
  line-height: 1rem;
}
</style>

<script lang="ts">
import {
  FetchUtils,
  SearchUserTO,
  UserRestControllerApiFactory,
  UserTO,
} from "@muenchen/digiwf-engine-api-internal";
import { AxiosResponse } from "axios";
import { defineComponent, PropType, ref, watch } from "vue";
import { Component, Prop, Vue, Watch } from "vue-property-decorator";

import { ApiConfig } from "../../api/ApiConfig";
import {
  callGetUserById,
  callGetUserByUsername,
  callSearchUser,
} from "../../api/user/userApiCalls";
import { mucatarURL } from "../../constants";
import { checkRequired } from "./validation/required";

export interface OnProperty {
  input: (value: any) => void;
}

export default defineComponent({
  props: {
    value: {
      // lhmObjectId of selected user
      type: String,
      required: false,
      default: undefined,
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
    on: {
      type: Object as PropType<OnProperty>,
      required: false,
      default: undefined,
    },
    schema: {
      type: Object,
      required: true,
    },
  },

  setup: (props: any) => {
    const schemaObj = JSON.parse(JSON.stringify(props.schema));
    const readonly: boolean = schemaObj.readOnly || false;
    const ldapGroups = schemaObj["ldap-groups"];

    /**
     * users which is already selected with complete user information.
     * each entry of props.value should have an entry in selectedUser
     */
    const selectedUser = ref<UserTO | undefined>(undefined);

    const searchText = ref("");
    const items = ref<UserTO[]>([]); // search result
    const isLoading = ref(false);
    /**
     * is true when initial loading is running, otherwise false
     */
    const locked = ref(false);
    const errorMessage = ref("");
    const lastSearch = ref("");

    const noDataText = ref<string>("Tippen, um Suche zu starten");

    watch(searchText, (newValue) => {
      searchUsersBySearchString(newValue);
    });

    const isReadonly = () => readonly || locked.value;

    const entries = () => items.value;

    const input = (value: string) => {
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
          selectedUser.value = user;
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

    const removeUser = (): void => {
      resetInput();
      selectedUser.value = undefined;
    };

    const mucatarUrl = (uid: string) => mucatarURL(uid);

    const searchUsersBySearchString = (searchString: string) => {
      if (!searchString || searchString.length < 3) return;

      if (lastSearch.value === searchString.slice(0, 3)) return;

      lastSearch.value = searchString.slice(0, 3);

      isLoading.value = true;

      noDataText.value = "Benutzer werden gesucht...";

      callSearchUser(lastSearch.value, ldapGroups)
        .then((users) => {
          if (lastSearch.value === searchText.value.slice(0, 3)) {
            items.value = users;
          }
          errorMessage.value = "";
        })
        .catch(() => {
          errorMessage.value = "Der Benutzer konnte nicht geladen werden.";
        })
        .finally(() => {
          isLoading.value = false;
          noDataText.value = "Tippen, um Suche zu starten";
        });
    };

    const resetInput = (): void => {
      lastSearch.value = "";
      searchText.value = "";
      items.value = [];
    };

    const getNamePrefix = (user: UserTO): string => {
      return user.forename!.slice(0, 1) + user.surname!.slice(0, 1);
    };

    const castNoAttrAvailable = (val: string): string => {
      if (val === "No_Attribute_Available") {
        return "-";
      }
      return val;
    };

    /*
      load initial value
     */

    if (readonly) {
      items.value = [selectedUser.value]; // in readonly: show initial values in autocomplete
    }
    if (props.value) {
      loadUser(props.value);
    }

    return {
      resetInput,
      input,
      getFullName,
      filterUsers,
      isRequired,
      isReadonly,
      selectedUser,
      isLoading,
      searchText,
      noDataText,
      entries,
      removeUser,
      mucatarUrl,
      castNoAttrAvailable,
      getNamePrefix,
    };
  },
});
</script>
