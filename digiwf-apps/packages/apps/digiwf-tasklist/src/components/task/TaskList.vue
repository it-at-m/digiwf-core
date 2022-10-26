<template>
  <div>
    <v-flex>
      <h1>{{ viewName }}</h1>
    </v-flex>
    <v-flex class="d-flex justify-space-between align-center searchField">
      <v-text-field
          id="suchfeld"
          v-model="syncedFilter"
          flat
          dense
          outlined
          hide-details
          label="Aufgaben durchsuchen"
          clearable
          append-icon="mdi-magnify"
          color="black"
          style="max-width: 500px"
      />
      <div class="d-flex align-center">
        <v-btn
            aria-label="Aufgaben aktualisieren"
            text
            color="primary"
            large
            @click="loadTasks"
        >
          <div style="min-width: 30px">
            <v-progress-circular
                v-if="isLoading"
                :size="25"
                width="2"
                color="primary"
                indeterminate
            />
            <v-icon
                v-else
            >
              mdi-refresh
            </v-icon>
          </div>
          Aktualisieren
        </v-btn>
      </div>
    </v-flex>
    <v-flex v-if="errorMessage">
      <AppToast
          :message="errorMessage"
          type="error"
      />
    </v-flex>
    <v-flex class="mt-10">
      <v-flex class="tableHeader">
        <v-flex class="headerTitel">
          Aufgabe
        </v-flex>
        <v-flex
            v-if="showAssignee"
            class="headerTitel"
            style="max-width: 148px"
        >
          Bearbeiter*in
        </v-flex>
        <v-flex
            class="headerTitel"
            style="max-width: 198px"
        >
          Vorgang
        </v-flex>
        <v-flex
            class="headerTitel"
            style="max-width: 80px"
        >
          Erstellt am
        </v-flex>
      </v-flex>
      <hr style="margin: 5px 0 0 0">
    </v-flex>
    <app-pageable-list
        :items="filteredTasks"
        found-data-text="Aufgaben gefunden"
        no-data-text="Keine Aufgaben gefunden"
    >
      <template #default="props">
        <template v-for="item in props.items">
          <slot :item=" {...item, searchInput: syncedFilter || ''}"/>
        </template>
      </template>
    </app-pageable-list>
  </div>
</template>

<style scoped>

.tableHeader {
  display: flex;
  margin: 0.5rem 45px 0 12px;

}

.headerTitel {
  margin: 0 5px;
  font-size: 0.9rem;
  font-weight: bold;
}

.searchField {
  margin: 1rem 0 1rem 0;
}
</style>

<script lang="ts">
import {Component, Emit, Prop, PropSync, Vue, Watch} from 'vue-property-decorator';
import {HumanTaskTO} from '@/api/api-client/api';
import AppToast from "@/components/UI/AppToast.vue";
import TaskItem from "@/components/task/TaskItem.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import AppPageableList from "@/components/UI/AppPageableList.vue";

@Component({
  components: {AppPageableList, TaskItem, AppToast, AppViewLayout}
})
export default class TaskList extends Vue {

  @PropSync('filter', { type: String })
  syncedFilter!: string

  @Prop()
  errorMessage: string | undefined;

  @Prop()
  isLoading: boolean | undefined;

  @Prop()
  tasks: HumanTaskTO[] | undefined;

  @Prop()
  viewName: string | undefined;

  @Prop()
  showAssignee: boolean | undefined;

  @Emit("loadTasks")
  loadTasks(): boolean {
    return true;
  }

  get filteredTasks(): HumanTaskTO[] | undefined {
    if (!this.syncedFilter) {
      return this.tasks;
    }

    if (this.tasks === undefined) {
      return [];
    }

    return this.tasks.filter(task => JSON.stringify(Object.values(task)).toLocaleLowerCase().includes(this.syncedFilter.toLocaleLowerCase()));
  }

}
</script>
