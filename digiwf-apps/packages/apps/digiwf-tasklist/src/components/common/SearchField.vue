<template>
  <!-- input.native to prevent this issue: https://github.com/vuetifyjs/vuetify/issues/4679 -->
  <v-combobox
    :items="persistentFilters?.map((f) => f.filterString) || []"
    :value="syncedFilter"
    aria-label="Aufgaben durchsuchen"
    class="searchField"
    clearable
    color="black"
    data-test="search-field"
    dense
    flat
    hide-details
    label="Aufgaben durchsuchen"
    outlined
    style="max-width: 500px"
    @change="changeFilter"
    @input.native="(e) => changeFilter(e.target.value)"
  >
    <template #append>
      <div class="v-input__icon">
        <v-btn
          v-if="isFilterPersistent()"
          aria-label="Filter löschen"
          class="v-icon"
          icon
          @click="deletePersistentFilter()"
        >
          <v-icon
            aria-hidden="false"
            aria-label="Filter löschen"
            color="primary"
            role="img"
          >
            mdi-star
          </v-icon>
        </v-btn>
        <v-btn
          v-else-if="showSaveBtn()"
          aria-label="Filter speichern"
          class="v-icon"
          icon
          @click="savePersistentFilter()"
        >
          <v-icon
            aria-hidden="false"
            aria-label="Filter speichern"
            color="primary"
            role="img"
          >
            mdi-star-outline
          </v-icon>
        </v-btn>
      </div>
      <v-icon
        aria-hidden="false"
        aria-label="Aufgaben durchsuchen"
        class="ml-2"
        role="img"
      >
        mdi-magnify
      </v-icon>
    </template>
  </v-combobox>
</template>

<script lang="ts">
import {FilterTO, SaveFilterTO} from "@muenchen/digiwf-engine-api-internal";
import debounce from "debounce";
import {defineComponent, ref} from "vue";

import {SEARCH_DEBOUNCE_INTERVAL} from "../../constants";
import {usePageId} from "../../middleware/pageId";
import {useGetPaginationData} from "../../middleware/paginationData";
import {
  useDeletePersistentFilters,
  useGetPersistentFilters,
  useSavePersistentFilters,
} from "../../middleware/persistentFilter/persistentFilters";

export default defineComponent({
  props: {
    onFilterChange: {
      type: Function,
      required: false,
    },
  },
  setup(props) {
    const {getSearchQueryOfUrl} = useGetPaginationData();
    const searchQuery = ref<string>(getSearchQueryOfUrl() || "");
    const pageId = usePageId();

    const {
      data: persistentFilters = ref([]),
      isLoading,
      isError: isLoadingError,
      refetch,
    } = useGetPersistentFilters();
    const saveMutation = useSavePersistentFilters();
    const deleteMutation = useDeletePersistentFilters();
    const errorMessage = ref<string>(
      isLoadingError ? "Filter konnten nicht geladen werden" : ""
    );

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
      const id = persistentFilters.value?.find(
        (f: FilterTO) => f.filterString == searchQuery.value
      )?.id;
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
      const isSaved =
        persistentFilters.value?.some(
          (f) => f.filterString === currentValue && f.pageId === pageId.id
        ) || false;
      return isNotBlank && isSaved;
    };

    const debouncedCallback =
      props.onFilterChange &&
      debounce(props.onFilterChange, SEARCH_DEBOUNCE_INTERVAL);

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
        if (debouncedCallback) {
          debouncedCallback(newFilter);
        }
      },
    };
  },
});
</script>

<style scoped>
.searchField {
  margin: 1rem 0 1rem 0;
}
</style>
