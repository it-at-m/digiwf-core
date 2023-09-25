<template>
  <div>
    <v-flex>
      <h1>{{ viewName }}</h1>
    </v-flex>
    <v-flex class="d-flex justify-space-between align-center searchField">
      <v-flex class="d-flex left align-center">
        <search-field
          :on-filter-change="(v) => $emit('changeFilter', v)"
        />
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

        ></v-text-field>
        <sort-by-select/>
      </v-flex>
      <div class="d-flex align-center">
        <v-btn
          aria-label="Aufgaben aktualisieren"
          text
          color="primary"
          large
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
        </v-btn>
      </div>
    </v-flex>
    <v-flex v-if="errorMessage">
      <AppToast :message="errorMessage" type="error"/>
    </v-flex>
    <v-flex class="mt-10">
      <v-flex class="tableHeader">
        <v-flex class="headerTitle"> Aufgabe</v-flex>
        <v-flex
          v-if="showAssignee"
          class="headerTitle"
          style="max-width: 148px"
        >
          Bearbeiter*in
        </v-flex>
        <v-flex class="headerTitle" style="max-width: 198px"> Vorgang</v-flex>
        <v-flex class="headerTitle" style="max-width: 80px">
          Erstellt am
        </v-flex>
      </v-flex>
      <hr style="margin: 5px 0 0 0"/>
    </v-flex>
    <v-data-iterator
      class="dataContainer"
      :items="tasks"
      found-data-text="Aufgaben gefunden"
      no-data-text="Keine Aufgaben gefunden"
      hide-default-footer
    >
      <template v-for="item in tasks">
        <slot :item="{ ...item, searchInput: filter || '' }"/>
      </template>
    </v-data-iterator>
  </div>
</template>

<style scoped>
.tableHeader {
  display: flex;
  margin: 0.5rem 45px 0 12px;
}

.headerTitle {
  margin: 0 5px;
  font-size: 0.9rem;
  font-weight: bold;
}
</style>

<script lang="ts">
import AppToast from "@/components/UI/AppToast.vue";
import SearchField from "../common/SearchField.vue";
import {HumanTask} from "../../middleware/tasks/tasksModels";
import {PropType} from "vue";
import SortBySelect from "../common/SortBySelect.vue";

export default {
  components: {SortBySelect, SearchField, AppToast},
  props: {
    filter: {
      type: String,
      default: "",
    },
    tag: {
      type: String
    },
    errorMessage: {
      type: String,
      default: undefined,
    },
    isLoading: {
      type: Boolean,
      required: true,
    },
    tasks: {
      type: Array as PropType<HumanTask[]>,
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
  },
  emits: {
    loadTasks: {
      type: Function as PropType<() => boolean>,
    },
    changeFilter: {
      type: Function as PropType<(newValue: string) => void>,
    },
    changeTag: {
      type: Function as PropType<(newValue: string) => void>,
    },
  },
};
</script>
