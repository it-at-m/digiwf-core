<template>
  <div>
    <v-flex>
      <h1>{{ viewName }}</h1>
    </v-flex>

    <task-list-filter
      :assignee="assignee"
      :filter="filter"
      :is-loading="isLoading"
      :show-assignee="showAssignee"
      :show-assignee-filter="showAssigneeFilter"
      :tag="tag"
      :tasks="tasks"
      description="Hier sehen Sie alle Aufgaben, die in Ihrer Gruppe aktuell bearbeitet werden. Klicken Sie auf übernehmen, um eine Aufgabe zu übernehmen."
      view-name="Gruppenaufgaben in Bearbeitung"
      @changeAssignee="(v) => $emit('changeAssignee', v)"
      @changeFilter="(v) => $emit('changeFilter', v)"
      @changeTag="(v) => $emit('changeTag', v)"
      @loadTasks="(v) => $emit('loadTasks', v)"
    />

    <v-flex v-if="errorMessage">
      <AppToast
        :message="errorMessage"
        type="error"
      />
    </v-flex>
    <v-flex class="mt-10">
      <v-flex class="tableHeader">
        <v-flex class="headerTitle">
          Aufgabe
        </v-flex>
        <v-flex
          v-if="showAssignee"
          class="headerTitle"
          style="max-width: 148px"
        >
          Bearbeiter*in
        </v-flex>
        <v-flex
          class="headerTitle"
          style="max-width: 198px"
        >
          Vorgang
        </v-flex>
        <v-flex
          class="headerTitle"
          style="max-width: 80px"
        >
          Erstellt am
        </v-flex>
      </v-flex>
      <hr style="margin: 5px 0 0 0">
    </v-flex>
    <v-list>
      <div v-if="tasks.length == 0">
        Keine Aufgaben gefunden
      </div>
      <template v-for="item in tasks">
        <slot :item="{ ...item, searchInput: filter || '' }" />
      </template>
    </v-list>
  </div>
</template>

<script lang="ts">
import {PropType} from "vue";

import AppToast from "@/components/UI/AppToast.vue";
import {HumanTask} from "../../middleware/tasks/tasksModels";
import TaskListFilter from "./TaskListFilter.vue";

export default {
  components: {TaskListFilter, AppToast},
  props: {
    filter: {
      type: String,
      default: "",
    },
    tag: {
      type: String,
    },
    assignee: {
      type: String,
      default: undefined,
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
      required: true,
    },
    /**
     * render the "Bearbeiter*in" column if true
     */
    showAssignee: {
      type: Boolean,
      default: false,
    },
    showAssigneeFilter: {
      type: Boolean,
      default: false,
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
    changeAssignee: {
      type: Function as PropType<(newValue: string) => void>,
    },
  },
};
</script>

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
