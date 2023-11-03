import {usePageId} from "../../middleware/pageId";
import {useStore} from "../../hooks/store";
import {ref, Ref, watch} from "vue";

export type FilterState = { general: { [key: string]: PageFiltersState } }

export interface PageFiltersState {
  readonly sortDirection: string;
}

const defaultPageFilterState: PageFiltersState = {
  sortDirection: "-createTime"
};

export const filters = {
  namespaced: true,
  state: {general: {}} as FilterState,
  getters: {
    getSortDirectionOfPage: (state: FilterState) => (pageId: string): string => {
      return state.general[pageId]?.sortDirection
        || defaultPageFilterState.sortDirection;

    },
  },
  mutations: {
    setSortDirectionOfPage: (state: FilterState, {pageId, sortDirection}: {
      pageId: string,
      sortDirection: string
    }) => {
      // deep copy of object. otherwise store.watch does not work
      const copy = JSON.parse(JSON.stringify(state.general));
      copy[pageId] = {
        ...copy[pageId],
        sortDirection,
      };
      state.general = copy;
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
  readonly currentSortDirection: Ref<string>;
  readonly sortDirections: SortDirection[];
}

export const usePageFilters = (): PageFilterData => {
  const pageId = usePageId().id;
  const store = useStore();
  const sortDirection = ref<string>(store.getters["filters/getSortDirectionOfPage"](pageId));

  store.watch((state) => state.filters.general[pageId],
    (newValue) => {
      if (newValue !== undefined) {
        sortDirection.value = newValue.sortDirection;
      }
    }, {
      // deep: true
    });

  watch(sortDirection, (newValue) => {
    store.commit("filters/setSortDirectionOfPage", {pageId, sortDirection: newValue});
  });

  return {
    currentSortDirection: sortDirection,
    sortDirections,
  };
};
