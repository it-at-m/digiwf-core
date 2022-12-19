<template>
    <v-list>
      <v-list-item :to="{ path: '/mytask' }">
        <v-list-item-content class="itemContent">
          <v-list-item-title class="navigationTitle">
            <span>Meine Aufgaben</span>
            <span class="counter grey--text text--darken-2">{{ numberOfMyTasks }}</span>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <hr class="hrDividerMenu">
      <v-list-item :to="{ path: '/instance' }">
        <v-list-item-content class="itemContent">
          <v-list-item-title class="navigationTitle">
            <span>Aktuelle Vorgänge</span>
            <span
              class="counter grey--text text--darken-2"
            >{{ numberOfProcessInstances }}</span>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <hr class="hrDividerMenu">
      <v-list-item :to="{ path: '/process' }">
        <v-list-item-content class="itemContent">
          <v-list-item-title>Vorgang Starten</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <hr
        class="hrDividerMenu"
        style="margin-bottom: 60px"
      >
      <p class="grey--text ml-9 mt-5 mb-0">
        Gruppenaufgaben
      </p>
      <v-list-item :to="{ path: '/opengrouptask' }">
        <v-list-item-content class="itemContent">
          <v-list-item-title class="navigationTitle">
            <span>Offen</span>
            <span
              class="counter grey--text  text--darken-2"
            >{{ numberOfOpenGroupTasks }}</span>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <hr class="hrDividerMenu">
      <v-list-item :to="{ path: '/assignedgrouptask' }">
        <v-list-item-content class="itemContent">
          <v-list-item-title class="navigationTitle">
            <span>in Bearbeitung</span>
            <span
              class="counter grey--text text--darken-2"
            >{{ numberOfAssignedGroupTasks }}</span>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <hr class="hrDividerMenu">
    </v-list>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useNumberOfTasks} from "@/middleware/tasks/taskMiddleware";

export default defineComponent({
  props: ["numberOfProcessInstances"],
  setup() {const numberOfTasks = useNumberOfTasks();
    return {
      numberOfMyTasks: numberOfTasks.myTasks,
      numberOfOpenGroupTasks: numberOfTasks.openGroupTasks,
      numberOfAssignedGroupTasks: numberOfTasks.assignedGroupTasks,
    }
  },
})
</script>

<style scoped>
.itemContent {
  margin: 5px 20px;
}

.counter {
  font-size: 0.9rem;
  font-weight: bold;
}

.navigationTitle {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  flex-direction: row;
}

</style>
