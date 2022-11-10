import {ActionContext} from "vuex";
import {RootState} from "../index";
import {FetchUtils, FilterRestControllerApiFactory, FilterTO} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

export interface FiltersState {
  filters: FilterTO[];
  lastFetch: number | null;
}

export default {
  namespaced: true,
  state: {
    filters: new Array<FilterTO>(),
    lastFetch: null
  } as FiltersState,
  getters: {
    shouldUpdate: (state: FiltersState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    filters(state: FiltersState): FilterTO[] {
      return state.filters;
    }
  },
  mutations: {
    setFilters(state: FiltersState, filters: FilterTO[]): void {
      state.filters = filters;
    },
    setLastFetch(state: FiltersState): void {
      state.lastFetch = new Date().getTime();
    }
  },
  actions: {
    async getFilters(context: ActionContext<FiltersState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());

      try {

        const res = await FilterRestControllerApiFactory(cfg).getFilters();
        context.commit('setFilters', res.data);
        context.commit('setLastFetch');
      } catch (err) {
        FetchUtils.defaultCatchHandler(err, "Die Filter konnten nicht geladen werden. Bitte versuchen Sie es erneut.");
      }

    }
  }
};
