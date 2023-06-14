<template>
  <app-view-layout>
    <div>
      <v-flex>
        <h1>Vorgänge</h1>
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
          label="Vorgänge durchsuchen"
          clearable
          color="black"
          style="max-width: 500px"
          @input.native="onFilterChanged"
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
                v-else-if="filter"
                icon
                aria-label="Filter löschen"
                class="v-icon"
                @click="savePersistentFilter()"
              >
                <v-icon color="primary">
                  mdi-star-outline
                </v-icon>
              </v-btn>
            </div>
            <v-icon class="ml-2">
              mdi-magnify
            </v-icon>
          </template>
        </v-combobox>

        <div class="d-flex align-center">
          <v-btn
            aria-label="Vorgänge aktualisieren"
            text
            style="padding-left: 13px;"
            large
            color="primary"
            @click="loadProcesses(true)"
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
      <pageable-list
        :items="filteredProcesses"
        found-data-text="Vorgänge gefunden"
        no-data-text="Keine Vorgänge gefunden"
      >
        <template #default="props">
          <template v-for="item in props.items">
            <process-definition-item
              :key="item.key"
              :item="item"
              :search-string="filter || ''"
            />
          </template>
        </template>
      </pageable-list>
    </div>
  </app-view-layout>
</template>

<style scoped>


.searchField {
  margin: 1rem 0 1rem 0;
}
</style>

<script lang="ts">
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import {FilterTO, SaveFilterTO, ServiceDefinitionTO} from '@muenchen/digiwf-engine-api-internal';
import ProcessDefinitionItem from "@/components/process/ProcessDefinitionItem.vue";
import AppPageableList from "@/components/UI/AppPageableList.vue";
import {
  deletePersistentFilterForNonHookCompatibleFunction,
  getPersistentFilterForNonHookCompatibleFunction,
  savePersistentFilterForNonHookCompatibleFunction
} from "../middleware/persistentFilter/persistentFilters";
import {defineComponent, ref, Ref} from "vue";
import store from "../store";
import {useRouter} from "vue-router/composables";

export default defineComponent({
  components: {PageableList: AppPageableList, ProcessDefinitionItem, AppToast, AppViewLayout},
  props: [],
  setup: () => {
    const router = useRouter();

    let processDefinitions: Ref<ServiceDefinitionTO[]> = ref([]);
    let isLoading: Ref<boolean> = ref(false);
    let filter: Ref<string> = ref("");
    let errorMessage: Ref<string> = ref("");
    let persistentFilters: Ref<FilterTO[]> = ref([]);
    let filteredProcesses: Ref<ServiceDefinitionTO[]> = ref([]);

    const created = () => {
      loadProcesses();
      loadFilter();
      loadPersistentFilters();
    };

    const loadProcesses = async (refresh = false): Promise<void> => {
      processDefinitions.value = store.getters['processDefinitions/processDefinitions'];
      isLoading.value = true;
      try {
        await store.dispatch('processDefinitions/loadProcessDefinitions', refresh);
        isLoading.value = false;
        errorMessage.value = "";
      } catch (error: any) {
        isLoading.value = false;
        errorMessage.value = error.message;
      }
      filterProcesses();
    };

    const loadFilter = () => {
      filter.value = router.currentRoute.query.filter as string ?? "";
      if (!filter.value) {
        filter.value = store.getters["processDefinitions/filter"];
        router.replace({query: {filter: filter.value}});
      }
      filterProcesses();
    };

    const onFilterChanged = (event: Event) => {
      const el = event.target as HTMLInputElement;
      filter.value = el.value;
      store.commit('processDefinitions/setFilter', filter);
      router.replace({path: "process", query: {filter: el.value}});
      filterProcesses();
    };

    const filterProcesses = () => {
      processDefinitions.value = store.getters['processDefinitions/processDefinitions'];
      if (!filter.value) {
        filteredProcesses.value = processDefinitions.value;
      }
      filteredProcesses.value = processDefinitions.value.filter(task => JSON.stringify(Object.values(task)).toLocaleLowerCase().includes(filter.value.toLocaleLowerCase()));
    };

    const isFilterPersistent = (): boolean => {
      if (
        !filter.value ||
        filter.value.length == 0 ||
        !persistentFilters.value ||
        persistentFilters.value.length == 0
      ) {
        return false;
      }
      return (
        persistentFilters.value.find(
          (fl: FilterTO) => fl.filterString == filter.value
        ) != undefined // can not work
      );
    };

    const savePersistentFilter = async () => {
      if (!filter.value) {
        return;
      }
      const request: SaveFilterTO = {
        pageId: "processes",
        filterString: filter.value,
      };
      savePersistentFilterForNonHookCompatibleFunction(request);
    };

    const deletePersistentFilter = () => {
      const id = persistentFilters.value.find((f: FilterTO) => f.filterString == filter.value)?.id;
      if (!id) {
        return;
      }
      deletePersistentFilterForNonHookCompatibleFunction(id);
    };

    const loadPersistentFilters = async (): Promise<void> => {
      try {
        const filterResponse = await getPersistentFilterForNonHookCompatibleFunction();
        persistentFilters.value = filterResponse.filter((filter: FilterTO) => filter.pageId === "processes");
        errorMessage.value = "";
      } catch (error: any) {
        errorMessage.value = error.message;
      }
    };

    created();
    return {
      processDefinitions,
      isLoading,
      filter,
      errorMessage,
      persistentFilters,
      onFilterChanged,
      savePersistentFilter,
      deletePersistentFilter,
      isFilterPersistent,
      filteredProcesses,
      loadProcesses
    };
  }
});
</script>
