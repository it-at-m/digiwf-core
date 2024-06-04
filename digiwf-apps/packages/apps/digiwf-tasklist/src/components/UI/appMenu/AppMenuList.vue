<template>
  <v-list>
    <AppMenuItem
      :number-of-elements="numberOfMyTasks"
      data-test="my-tasks-btn"
      label="Meine Aufgaben"
      path="/mytask"
    />
    <hr class="hrDividerMenu">
    <AppMenuItem
      :number-of-elements="numberOfProcessInstances"
      data-test="current-instances-btn"
      label="Meine Vorgänge"
      path="/instance"
    />
    <hr class="hrDividerMenu">
    <AppMenuItem
      data-test="start-process-btn"
      label="Vorgang Starten"
      path="/process"
    />
    <hr
      class="hrDividerMenu"
      style="margin-bottom: 60px"
    >
    <p class="grey--text ml-9 mt-5 mb-0">
      Gruppenaufgaben
    </p>
    <AppMenuItem
      :number-of-elements="numberOfOpenGroupTasks"
      data-test="open-group-tasks-btn"
      label="Offen"
      path="/opengrouptask"
    />
    <hr class="hrDividerMenu">
    <AppMenuItem
      :number-of-elements="numberOfAssignedGroupTasks"
      data-test="assigned-group-tasks-btn"
      label="In Bearbeitung"
      path="/assignedgrouptask"
    />
    <hr class="hrDividerMenu">
  </v-list>
</template>

<script lang="ts">
import {defineComponent} from "vue";

import {useNumberOfTasks} from "@/middleware/tasks/taskMiddleware";
import AppMenuItem from "./AppMenuItem.vue";

export default defineComponent({
  components: {AppMenuItem},
  props: ["numberOfProcessInstances"],
  setup() {
    const numberOfTasks = useNumberOfTasks();
    return {
      numberOfMyTasks: numberOfTasks.myTasks,
      numberOfOpenGroupTasks: numberOfTasks.openGroupTasks,
      numberOfAssignedGroupTasks: numberOfTasks.assignedGroupTasks,
    };
  },
});
</script>

<style scoped></style>
