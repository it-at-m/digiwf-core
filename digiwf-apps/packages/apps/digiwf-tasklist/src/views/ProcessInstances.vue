<template>
  <app-view-layout>
    <div>
      <v-flex>
        <h1>Aktuelle Vorgänge</h1>
      </v-flex>
      <v-flex class="d-flex justify-space-between align-center searchField">
      <!-- input.native to prevent this issue: https://github.com/vuetifyjs/vuetify/issues/4679 -->
      <v-combobox
        id="suchfeld"
        v-model="filter"
        :items="persistentFilters.map((f) => f.filterString)"
        flat
        dense
        outlined
        hide-details
        label="Aufgaben durchsuchen"
        clearable
        color="black"
        style="max-width: 500px"
        @input.native="filter=$event.srcElement.value"
      >
        <template #append>
          <div class="v-input__icon">
            <v-btn
              v-if="isFilterPersistent"
              icon
              aria-label="Filter speichern"
              class="v-icon yellow--text"
              @click="deletePersistentFilter()"
            >
              <v-icon color="yellow"> mdi-star </v-icon>
            </v-btn>
            <v-btn
              v-else
              icon
              aria-label="Filter löschen"
              class="v-icon yellow--text"
              @click="savePersistentFilter()"
            >
              <v-icon color="yellow"> mdi-star-outline </v-icon>
            </v-btn>
          </div>
          <v-icon class="ml-2"> mdi-magnify </v-icon>
        </template>
      </v-combobox>
        <div class="d-flex align-center">
          <v-btn
            aria-label="Vorgänge aktualisieren"
            style="padding-left: 13px;"
            large
            text
            color="primary"
            @click="loadMyProcessInstances(true)"
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
            Vorgang
          </v-flex>
          <v-flex
            class="headerTitel"
            style="max-width: 145px"
          >
            Status
          </v-flex>
          <v-flex
            class="headerTitel"
            style="max-width: 100px"
          >
            Erstellt am
          </v-flex>
        </v-flex>
        <hr style="margin: 5px 0 0 0">
      </v-flex>
      <app-pageable-list
        :items="filteredProcessInstances"
        found-data-text="Vorgänge gefunden"
        no-data-text="Keine laufenden Vorgänge gefunden"
      >
        <template #default="props">
          <template v-for="item in props.items">
            <process-instance-item
              :key="item.id"
              :item="item"
              :search-string="filter || ''"
            />
          </template>
        </template>
      </app-pageable-list>
    </div>
  </app-view-layout>
</template>

<style scoped>

.tableHeader {
  display: flex;
  margin: 0.5rem 50px 0 12px;

}

.headerTitel {
  margin: 0 5px;
  font-size: 0.9rem;
  font-weight: bold;
}

.searchField {
  margin: 1rem 0px 1rem 0;
}
</style>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator';
import AppToast from "@/components/UI/AppToast.vue";
import TaskItem from "@/components/task/TaskItem.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import {ServiceInstanceTO, FilterTO, SaveFilterTO, FilterRestControllerApiFactory, FetchUtils} from '@muenchen/digiwf-engine-api-internal';
import AppPageableList from "@/components/UI/AppPageableList.vue";
import ProcessInstanceItem from "@/components/process/ProcessInstanceItem.vue";
import {ApiConfig} from "../api/ApiConfig";

@Component({
  components: {ProcessInstanceItem, AppPageableList, TaskItem, AppToast, AppViewLayout}
})
export default class ProcessInstances extends Vue {

  processInstances: ServiceInstanceTO[] = [];
  isLoading = false;
  filter = "";
  errorMessage = "";
  persistentFilters: FilterTO[] = [];

  created(): void {
    this.loadMyProcessInstances();
    this.loadFilter();
    this.loadPersistentFilters();
  }

  async loadMyProcessInstances(refresh = false): Promise<void> {
    this.processInstances = this.$store.getters['processInstances/processInstances'];
    this.isLoading = true;
    const startTime = new Date().getTime();
    try {
      await this.$store.dispatch('processInstances/getProcessInstances', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
    setTimeout(() => this.isLoading = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  loadFilter(): void {
    this.filter = this.$store.getters["processInstances/filter"];
  }

  @Watch("filter")
  onFilterChanged(filter: string) {
    this.$store.commit('processInstances/setFilter', this.filter);
  }

  get filteredProcessInstances(): ServiceInstanceTO[] {
    this.processInstances = this.$store.getters['processInstances/processInstances'];
    if (!this.filter) {
      return this.processInstances;
    }
    return this.processInstances.filter(task => JSON.stringify(Object.values(task)).toLocaleLowerCase().includes(this.filter.toLocaleLowerCase()));
  }

  get isFilterPersistent(): boolean {
    if (
      !this.filter ||
      this.filter.length == 0 ||
      !this.persistentFilters ||
      this.persistentFilters!.length == 0
    ) {
      return false;
    }
    return (
      this.persistentFilters!.find(
        (fl: FilterTO) => fl.filterString == this.filter
      ) != undefined
    );
  }

  async savePersistentFilter() {
    if (!this.filter){
      return;
    }
    const request: SaveFilterTO = {
      pageId: "processinstances",
      filterString: this.filter,
    }
    try {
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
      await FilterRestControllerApiFactory(cfg).saveFilter(request);

      this.errorMessage = "";
      this.$store.dispatch('filters/getFilters', true);
    } catch (error) {
      this.errorMessage = 'Der Filter konnte nicht gespeichert werden.';
    }
  }

  async deletePersistentFilter() {
    const id = this.persistentFilters!.find((f: FilterTO) => f.filterString == this.filter)?.id!
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
    this.persistentFilters = this.$store.getters['filters/filters'].filter((filter: FilterTO) => filter.pageId === "processinstances");
    try {
      await this.$store.dispatch('filters/getFilters', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
  }

  @Watch('$store.state.filters.filters')
  setPersistentFilters(): void {
    this.persistentFilters = this.$store.getters['filters/filters'].filter((filter: FilterTO) => filter.pageId === 'processinstances');
  }

}
</script>
