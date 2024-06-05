<template>
  <v-list-item
    :aria-label="
      task.inFinishProcess
        ? 'Aufgabe wird gerade abgeschlossen'
        : 'Aufgabe ' + task.name + ' öffnen'
    "
    :style="
      task.inFinishProcess &&
        'background-color: #F8F8F8; border-radius:6px; cursor: not-allowed; color: #AAA'
    "
    :to="!task.inFinishProcess ? { path: '/task/' + task.id } : ''"
    class="d-flex align-center"
  >
    <v-flex
      class="d-flex flex-column taskColumn"
      style="min-height: 4.5rem; max-height: 6.5rem; margin: 8px 0"
    >
      <h2 class="taskTitle">
        <text-highlight :queries="searchString">
          {{ task.name }}
        </text-highlight>
        <v-chip
          v-if="task.tag"
          small
          @click.prevent="$emit('clickTag', task.tag)"
        >
          {{ task.tag }}
        </v-chip>
      </h2>
      <p
        v-if="task.inFinishProcess"
        class="grey--text"
        data-test="task-is-completing"
        style="font-size: 0.9rem"
      >
        <v-icon>mdi-progress-clock</v-icon>
        Task wird aktuell abgeschlossen
      </p>
      <p
        v-if="task.followUpDateFormatted"
        class="grey--text"
        style="font-size: 0.9rem"
      >
        Wiedervorlage am {{ task.followUpDateFormatted }}
      </p>
      <p>
        <text-highlight :queries="searchString">
          {{ task.description }}
        </text-highlight>
      </p>
    </v-flex>
    <v-flex
      class="taskColumn"
      style="min-width: 200px; max-width: 200px"
    >
      <p class="taskInfo">
        <text-highlight :queries="searchString">
          {{ task.processName }}
        </text-highlight>
      </p>
    </v-flex>
    <v-flex
      class="taskColumn"
      style="min-width: 80px; max-width: 80px"
    >
      <p class="taskInfo">
        {{ task.createTime }}
      </p>
    </v-flex>
    <v-flex
      class="d-flex justify-end align-center ml-2"
      style="min-width: 25px; max-width: 25px"
    >
      <v-menu
        v-if="!task.inFinishProcess"
        offset-x
        top
      >
        <template #activator="{ on, attrs }">
          <v-btn
            aria-hidden="false"
            aria-label="Aktionen für die Aufgabe"
            icon
            v-bind="attrs"
            v-on.prevent="on"
            @click="
              (event) => {
                event.preventDefault();
              }
            "
          >
            <v-icon
              aria-hidden="false"
              aria-label="Aktionen für die Aufgabe"
              role="img"
            >
              mdi-dots-vertical
            </v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            :aria-label="'Aufgabe ' + task.name + ' öffnen'"
            :to="{ path: '/task/' + task.id }"
            link
            @click="
              (event) => {
                event.preventDefault();
              }
            "
          >
            <div>Öffnen</div>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-flex>
  </v-list-item>
</template>

<script lang="ts">
import {PropType} from "vue";

import {HumanTask} from "../../middleware/tasks/tasksModels";

export default {
  props: {
    task: {
      type: Object as PropType<HumanTask>,
      required: true,
    },
    searchString: {
      type: String,
      default: "",
    },
  },
  emits: {
    clickTag: {
      type: Function as PropType<(tag: string) => void>,
    },
  },
};
</script>

<style scoped>
.taskColumn {
  margin: 0 0 0 8px;
  align-self: baseline;
  overflow: hidden;
}

.taskTitle {
  font-size: 1.1rem;
  font-weight: 600;
  display: flex;
  justify-content: space-between;
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
