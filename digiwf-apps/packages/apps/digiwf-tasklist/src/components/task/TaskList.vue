<template>
  <div>
    <v-flex>
      <h1>{{ viewName }}</h1>
    </v-flex>
    <v-flex class="d-flex justify-space-between align-center searchField">
      <!-- input.native to prevent this issue: https://github.com/vuetifyjs/vuetify/issues/4679 -->
      <v-combobox
        id="suchfeld"
        v-model="syncedFilter"
        :items="persistentFilters.map((f) => f.filterString)"
        flat
        dense
        outlined
        hide-details
        label="Aufgaben durchsuchen"
        clearable
        color="black"
        style="max-width: 500px"
        @input.native="syncedFilter=$event.srcElement.value"
      >
        <template #append>
          <div class="v-input__icon">
            <v-btn
              v-if="isFilterPersistent"
              icon
              aria-label="Filter speichern"
              class="v-icon"
              @click="deletePersistentFilter()"
            >
              <v-icon color="primary"> mdi-star</v-icon>
            </v-btn>
            <v-btn
              v-else-if="syncedFilter"
              icon
              aria-label="Filter löschen"
              class="v-icon"
              @click="savePersistentFilter()"
            >
              <v-icon color="primary"> mdi-star-outline</v-icon>
            </v-btn>
          </div>
          <v-icon class="ml-2"> mdi-magnify</v-icon>
        </template>
      </v-combobox>
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
        <v-flex class="headerTitel"> Aufgabe</v-flex>
        <v-flex
          v-if="showAssignee"
          class="headerTitel"
          style="max-width: 148px"
        >
          Bearbeiter*in
        </v-flex>
        <v-flex class="headerTitel" style="max-width: 198px"> Vorgang</v-flex>
        <v-flex class="headerTitel" style="max-width: 80px">
          Erstellt am
        </v-flex>
      </v-flex>
      <hr style="margin: 5px 0 0 0"/>
    </v-flex>
    <app-pageable-list
      :items="filteredTasks"
      found-data-text="Aufgaben gefunden"
      no-data-text="Keine Aufgaben gefunden"
    >
      <template #default="props">
        <template v-for="item in props.items">
          <slot :item="{ ...item, searchInput: syncedFilter || '' }"/>
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
import {Component, Emit, Prop, PropSync, Vue, Watch} from "vue-property-decorator";
import AppToast from "@/components/UI/AppToast.vue";
import TaskItem from "@/components/task/TaskItem.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import AppPageableList from "@/components/UI/AppPageableList.vue";
import {
  FetchUtils,
  FilterRestControllerApiFactory,
  FilterTO,
  HumanTaskTO,
  SaveFilterTO
} from "@muenchen/digiwf-engine-api-internal";
import {ApiConfig} from "../../api/ApiConfig";

@Component({
  components: {AppPageableList, TaskItem, AppToast, AppViewLayout},
})
export default class TaskList extends Vue {
  @PropSync("filter", {type: String})
  syncedFilter!: string;

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

  @Prop()
  pageId!: string;

  persistentFilters: FilterTO[] = [];

  @Emit("loadTasks")
  loadTasks(): boolean {
    return true;
  }

  created(): void {
    this.loadPersistentFilters();
  }

  get filteredTasks(): HumanTaskTO[] | undefined {
    if (!this.syncedFilter) {
      return this.tasks;
    }

    if (this.tasks === undefined) {
      return [];
    }

    return this.tasks.filter((task) =>
      JSON.stringify(Object.values(task))
        .toLocaleLowerCase()
        .includes(this.syncedFilter.toLocaleLowerCase())
    );
  }

  get isFilterPersistent(): boolean {
    if (
      !this.syncedFilter ||
      this.syncedFilter.length == 0 ||
      !this.persistentFilters ||
      this.persistentFilters!.length == 0
    ) {
      return false;
    }
    return (
      this.persistentFilters!.find(
        (fl: FilterTO) => fl.filterString == this.syncedFilter
      ) != undefined
    );
  }

  async savePersistentFilter() {
    const persistentFilter: SaveFilterTO = {
      pageId: this.pageId,
      filterString: this.syncedFilter,
    }
    try {
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
      await FilterRestControllerApiFactory(cfg).saveFilter(persistentFilter);

      this.errorMessage = "";
      this.$store.dispatch('filters/getFilters', true);
    } catch (error) {
      this.errorMessage = 'Der Filter konnte nicht gespeichert werden.';
    }
  }

  async deletePersistentFilter() {
    const id = this.persistentFilters!.find((f: FilterTO) => f.filterString == this.syncedFilter)?.id!
    try {
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getDELETEConfig());
      await FilterRestControllerApiFactory(cfg)._delete(id);

      this.errorMessage = "";
      this.$store.dispatch('filters/getFilters', true);
    } catch (error) {
      this.errorMessage = 'Der Filter konnte nicht gelöscht werden.';
    }
  }

  async loadPersistentFilters(refresh = false): Promise<void> {
    this.persistentFilters = this.$store.getters['filters/filters'].filter((filter: FilterTO) => filter.pageId === this.pageId);
    try {
      await this.$store.dispatch('filters/getFilters', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
  }

  @Watch('$store.state.filters.filters')
  setPersistentFilters(): void {
    this.persistentFilters = this.$store.getters['filters/filters'].filter((filter: FilterTO) => filter.pageId === this.pageId);
  }

}
</script>
