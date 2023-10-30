<template>
  <!-- input.native to prevent this issue: https://github.com/vuetifyjs/vuetify/issues/4679 -->
  <v-combobox
    id="suchfeld"
    class="searchField"
    :value="syncedFilter"
    data-cy="search-field"
    color="black"
    flat
    dense
    outlined
    hide-details
    :items="persistentFilters?.map((f) => f.filterString) || []"
    aria-label="Aufgaben durchsuchen"
    label="Aufgaben durchsuchen"
    clearable
    style="max-width: 500px"
    @change="changeFilter"
    @input.native="(e) => changeFilter(e.target.value)"
  >
    <template #append>
      <div class="v-input__icon">
        <v-btn
          v-if="isFilterPersistent()"
          icon
          aria-label="Filter löschen"
          class="v-icon"
          @click="deletePersistentFilter()"
        >
          <v-icon color="primary" aria-label="Filter löschen" role="img" aria-hidden="false">
            mdi-star
          </v-icon>
        </v-btn>
        <v-btn
          v-else-if="showSaveBtn()"
          icon
          aria-label="Filter speichern"
          class="v-icon"
          @click="savePersistentFilter()"
        >
          <v-icon color="primary" aria-label="Filter speichern" role="img" aria-hidden="false">
            mdi-star-outline
          </v-icon>
        </v-btn>
      </div>
      <v-icon class="ml-2" aria-label="Aufgaben durchsuchen" role="img" aria-hidden="false">
        mdi-magnify
      </v-icon>
    </template>
  </v-combobox>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import debounce from "debounce";
import {FilterTO, SaveFilterTO} from "@muenchen/digiwf-engine-api-internal";
import {usePageId} from "../../middleware/pageId";
import {useGetPaginationData} from "../../middleware/paginationData";
import {
  useDeletePersistentFilters,
  useGetPersistentFilters,
  useSavePersistentFilters
} from "../../middleware/persistentFilter/persistentFilters";
import {SEARCH_DEBOUNCE_INTERVAL} from "../../constants";

export default defineComponent({
  props:{
    onFilterChange: {
      type: Function,
      required: false,
    }
  },
  setup(props) {
    const {getSearchQueryOfUrl} = useGetPaginationData();
    const searchQuery = ref<string>(getSearchQueryOfUrl() || "");
    const pageId = usePageId();

    const {data: persistentFilters = ref([]), isLoading, isError: isLoadingError, refetch} = useGetPersistentFilters();
    const saveMutation = useSavePersistentFilters();
    const deleteMutation = useDeletePersistentFilters();
    const errorMessage = ref<string>(isLoadingError ? "Filter konnten nicht geladen werden" : "");

    const savePersistentFilter = () => {
      const newValue = searchQuery.value;
      if (!newValue) {
        return;
      }
      const persistentFilter: SaveFilterTO = {
        pageId: pageId.id || pageId.path,
        filterString: newValue || "",
      };
      saveMutation.mutateAsync(persistentFilter).catch(() => {
        errorMessage.value = "Der Filter konnte nicht gespeichert werden.";
      });
    };
    const deletePersistentFilter = () => {
      const id = persistentFilters.value?.find((f: FilterTO) => f.filterString == searchQuery.value)?.id;
      if (!id) {
        return;
      }
      deleteMutation.mutateAsync(id).catch(() => {
        errorMessage.value = "Der Filter konnte nicht gelöscht werden.";
      });
    };

    const isFilterPersistent = (): boolean => {
      const currentValue = searchQuery.value || "";
      const isNotBlank: boolean = currentValue.trim().length > 0;
      const isSaved = persistentFilters.value?.some(f => f.filterString === currentValue && f.pageId === pageId.id) || false;
      return isNotBlank && isSaved;
    };

    const debouncedCallback = props.onFilterChange && debounce(props.onFilterChange, SEARCH_DEBOUNCE_INTERVAL);

    return {
      isLoading,
      syncedFilter: searchQuery,
      isFilterPersistent,
      showSaveBtn: () => {
        const currentValue = searchQuery.value || "";
        return currentValue.trim()?.length > 0 && !isFilterPersistent();
      },
      persistentFilters,
      deletePersistentFilter,
      loadTasks: refetch,
      savePersistentFilter,
      changeFilter: (newFilter: string) => {
        searchQuery.value = newFilter;
        if(debouncedCallback) {
          debouncedCallback(newFilter);
        }
      },
    };
  }
});

</script>

<style scoped>
.searchField {
  margin: 1rem 0 1rem 0;
}
</style>
