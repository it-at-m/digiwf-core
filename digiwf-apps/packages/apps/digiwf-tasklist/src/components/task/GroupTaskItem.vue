<template>
  <v-list-item
    :aria-label="'Gruppenaufgabe '+ task.name+ ' öffnen'"
    class="d-flex align-center"
    :style="(task.inAssignProcess && !showAssignee) && 'background-color: #F8F8F8; border-radius:6px; cursor: not-allowed; color: #AAA'"
    :to="(!task.inAssignProcess ||showAssignee) && '/opengrouptask/'+ task.id"
  >
    <v-flex
      class="d-flex flex-column taskColumn"
      style="min-height: 5rem; max-height: 6.5rem; margin: 8px 0"
    >
      <h2 class="taskTitle">
        <text-highlight :queries="searchString">
          {{ task.name }}
        </text-highlight>
      </h2>
      <p
        v-if="task.inAssignProcess && !showAssignee"
        class="grey--text"
        style="font-size: 0.9rem"
      >
        <v-icon>mdi-progress-clock</v-icon> Task wird aktuell einer Person zugewiesen
      </p>
      <p
        v-if="task.followUpDate"
        class="grey--text"
        style="font-size: 0.9rem"
      >
        Wiedervorlage am {{ task.followUpDate }}
      </p>
      <p>
        <text-highlight :queries="searchString">
          {{ task.description }}
        </text-highlight>
      </p>
    </v-flex>
    <v-flex
      v-if="showAssignee"
      style="min-width: 150px; max-width: 150px"
      class="taskColumn"
    >
      <p v-if="task.assigneeFormatted">
        <text-highlight :queries="searchString">
          {{ task.assigneeFormatted }}
        </text-highlight>
      </p>
    </v-flex>
    <v-flex
      style="min-width: 200px; max-width: 200px"
      class="taskColumn"
    >
      <p class="taskInfo">
        <text-highlight :queries="searchString">
          {{ task.processName }}
        </text-highlight>
      </p>
    </v-flex>
    <v-flex
      style="min-width: 80px; max-width: 80px"
      class="taskColumn"
    >
      <p class="taskInfo">
        {{ task.createTime }}
      </p>
    </v-flex>
    <v-flex
      style="min-width: 25px; max-width: 25px"
      class="d-flex justify-end align-center ml-2"
    >
      <v-menu
        top
        offset-x
      >
        <template #activator="{ on, attrs }">
          <v-btn
            icon
            v-bind="attrs"
            @click="(event) => { event.preventDefault()}"
            v-on.prevent="on"
          >
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            :aria-label="'Aufgabe '+ task.name+ ' öffnen'"
            link
            :to="'/opengrouptask/'+task.id"
            @click="(event) => { event.preventDefault()}"
          >
            <v-list-item-title>Öffnen</v-list-item-title>
          </v-list-item>
          <v-list-item
            :aria-label="'Aufgabe '+task.name+ ' bearbeiten'"
            link
            @click="(event) => {
              $emit('edit', task.id);
              event.preventDefault();
            }"
          >
            <v-list-item-title>Bearbeiten</v-list-item-title>
          </v-list-item>
          <v-list-item
            :aria-label="'Aufgabe '+task.name+ ' zuweisen'"
            link
            @click="() => dialogOpen = true"
          >
            <v-list-item-title>Zuweisen</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-flex>
    <assign-task-dialog
      v-if="dialogOpen"
      :open="true"
      :task-name="task.name"
      :task-id="task.id"
      @close="dialogOpen = false"
    />
  </v-list-item>
</template>

<script lang="ts">
import {HumanTask} from "../../middleware/tasks/tasksModels";
import {PropType, ref} from "vue";
import AssignTaskDialog from "./AssignTaskDialog.vue";

export default {
  components: {AssignTaskDialog},
  props: {
    task: {
      type: Object as PropType<HumanTask>,
      required: true
    },
    searchString: {
      type: String,
      default: ""
    },
    showAssignee: {
      type: Boolean,
      default: false
    }
  },
  emits: {
    edit: {
      type: Function as PropType<(id: string) => void>
    },
  },
  setup: () => {
    const dialogOpen = ref<boolean>(false);
    return {
      dialogOpen
    };
  }
};

</script>

<style scoped>

.taskColumn {
  margin: 0 0 0 8px;
  align-self: baseline;
  overflow: hidden;
}

.taskTitle {
  font-size: 1.2rem;
}

.taskInfo {
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  margin-bottom: 0.3rem;
}

.taskInfo span {
  margin-right: 0.5rem;
}

</style>
