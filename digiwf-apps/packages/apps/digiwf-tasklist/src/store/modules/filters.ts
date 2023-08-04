import {usePageId} from "../../middleware/pageId";
import {useStore} from "../../hooks/store";
import {ref, Ref} from "vue";

export type FilterState = { general: PageFiltersState[] }

export interface PageFiltersState {
  readonly pageId: string
  readonly sortDirection: string;
}

const defaultPageFilterState: PageFiltersState = {
  pageId: "",
  sortDirection: "-createTime"
};

export const filters = {
  namespaced: true,
  state: {general: []} as FilterState,
  getters: {
    getSortDirectionOfPage: (state: FilterState) => (pageId: string): string => {
      const sortDirection = state.general.find(it => it.pageId === pageId)?.sortDirection || defaultPageFilterState.sortDirection;
      return sortDirection;
    },
  },
  mutations: {
    setSortDirectionOfPage: (state: FilterState, {pageId, sortDirection}: {
      pageId: string,
      sortDirection: string
    }) => {
      if (!state.general.find(it => it.pageId === pageId)) {
        state.general.push({
          pageId,
          sortDirection
        });
      }
      const newState = state.general.map((it) => it.pageId === pageId ? {...it, sortDirection} : it);
      state.general = newState;
    }
  },
};

export interface SortDirection {
  readonly text: string;
  readonly value: string;
}

const sortDirections: SortDirection[] = [
  {
    value: "-createTime",
    text: "Neueste zuerst"
  },
  {
    value: "+createTime",
    text: "Älteste zuerst",
  },
];

export interface PageFilterData {
  readonly current: Ref<PageFiltersState>;
  readonly sortDirections: SortDirection[]
}

export const usePageFilters = (): PageFilterData => {
  const pageId = usePageId().id;
  const store = useStore();
  const filters = ref<PageFiltersState>(store.getters["filters/getSortDirectionOfPage"](pageId));

  store.watch((state) => state.filters.general.find(it => it.pageId === pageId),
    (newValue) => {
    console.log("newValue:", newValue);
      if(newValue !== undefined) {
        filters.value = newValue;
      }
    }, {
      deep: true
    });

  return {
    current: filters,
    sortDirections,
  };
};


export type UseUpdatePageFilterMethod = (updatedValues: Partial<PageFiltersState>) => void;
export const useUpdatePageFilters = (): UseUpdatePageFilterMethod => {
  const pageId = usePageId().id;
  const store = useStore();

  return (updatedValues) => {
    if (updatedValues.sortDirection) {
      store.commit("filters/setSortDirectionOfPage", {pageId, sortDirection: updatedValues.sortDirection});
    }
  };
};
