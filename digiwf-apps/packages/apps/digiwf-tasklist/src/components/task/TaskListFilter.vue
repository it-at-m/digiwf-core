<template>
  <div>
    <v-flex class="d-flex justify-space-between align-center searchField">
      <v-flex class="d-flex left align-center">
        <search-field
          :on-filter-change="(v) => $emit('changeFilter', v)"
        />
        <dwf-button
          aria-label="Weitere Filter"
          class="advanced-filter-button"
          @click="advancedFilterOpen = !advancedFilterOpen"
        >

          <v-icon>mdi-filter</v-icon>
          Filter

          <v-chip
            v-if="!advancedFilterOpen && tag"
            color="primary"
            class="filter-badge"
            close
            @click:close="() => $emit('changeTag', undefined)"
          >
            Tag: {{ tag }}
          </v-chip>

          <user-filter-badge
            v-if="!advancedFilterOpen && assignee"
            class="filter-badge"
            :user-id="assignee"
            @clear="() => $emit('changeAssignee', undefined)"
          />
        </dwf-button>
        <sort-by-select/>
      </v-flex>
      <div class="d-flex align-center">
        <dwf-button
          aria-label="Aufgaben aktualisieren"
          @click="$emit('loadTasks')"
        >
          <div style="min-width: 30px">
            <v-progress-circular
              v-if="isLoading"
              :size="25"
              width="2"
              color="primary"
              indeterminate
            />
            <v-icon v-else> mdi-refresh</v-icon>
          </div>
          Aktualisieren
        </dwf-button>
      </div>
    </v-flex>
    <v-flex
      v-if="advancedFilterOpen"
      class="d-flex advanced-filter"
    >
      <v-text-field
        label="Tag"
        outlined
        flat
        dense
        hide-details
        clearable
        :value="tag"
        @change="(v) => $emit('changeTag', v)"
        @input.native="(e) => $emit('changeTag', e.target.value)"
      />
      <base-ldap-input
        v-if="showAssigneeFilter"
        :rules="[]"
        :value="assignee"
        :clearable="true"
        flat
        dense
        @input="(value) =>$emit('changeAssignee', value)"
      />
    </v-flex>
  </div>
</template>

<script lang="ts">

import {defineComponent, PropType, ref} from "vue";
import {HumanTask} from "../../middleware/tasks/tasksModels";
import BaseLdapInput from "../form/BaseLdapInput.vue";
import SortBySelect from "../common/SortBySelect.vue";
import DwfButton from "../common/DwfButton.vue";
import SearchField from "../common/SearchField.vue";
import UserFilterBadge from "./filter/UserFilterBadge.vue";

export default defineComponent({
  components: {UserFilterBadge, SearchField, DwfButton, SortBySelect, BaseLdapInput},
  props: {
    filter: {
      type: String,
      default: "",
    },
    tag: {
      type: String
    },
    assignee: {
      type: String,
      default: undefined,
    },
    isLoading: {
      type: Boolean,
      required: true,
    },
    tasks: {
      type: Array as PropType<HumanTask[]>,
      // eslint-disable-next-line vue/require-valid-default-prop
      default: [],
    },
    viewName: {
      type: String,
      required: true
    },
    /**
     * render the "Bearbeiter*in" column if true
     */
    showAssignee: {
      type: Boolean,
      default: false
    },
    showAssigneeFilter: {
      type: Boolean,
      default: false
    },
  },
  emits: [
    "loadTasks", // Function as PropType<() => boolean>,
    "changeFilter", // Function as PropType<(newValue: string) => void>,
    "changeTag", //  Function as PropType<(newValue: string) => void>,
    "changeAssignee"//  Function as PropType<(newValue: string) => void>,
  ],
  setup: () => {
    const advancedFilterOpen = ref<boolean>(false);
    return {
      advancedFilterOpen
    };
  }
});

</script>

<style scoped>
.advanced-filter-button {
  margin-left: 1rem;
  margin-right: 1rem;
}

.advanced-filter {
  margin-top: 10pt;
}

.filter-badge {
  margin-left: 5pt;
}
</style>
