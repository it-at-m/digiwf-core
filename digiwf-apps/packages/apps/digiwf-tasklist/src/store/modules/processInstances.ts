import {ActionContext} from "vuex";
import {RootState} from "@/store";
import {TasksState} from "@/store/modules/tasks";
import {ServiceInstanceControllerApiFactory, ServiceInstanceTO} from '@/api/api-client/api';
import FetchUtils from "@/api/FetchUtils";

export interface ProcessInstancesState {
  processInstances: ServiceInstanceTO[];
  lastFetch: number;
}

export default {
  namespaced: true,
  state: {
    processInstances: new Array<ServiceInstanceTO>(),
    lastFetch: 0
  } as ProcessInstancesState,
  getters: {
    shouldUpdate: (state: TasksState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    processInstances(state: ProcessInstancesState): ServiceInstanceTO[] {
      return state.processInstances.filter(Boolean).sort((a, b) => b.startTime!.localeCompare(a.startTime!));
    }
  },
  mutations: {
    setProcessInstances(state: ProcessInstancesState, processInstances: ServiceInstanceTO[]): void {
      state.processInstances = processInstances;
    },
    setLastFetch(state: TasksState, date: number): void {
      state.lastFetch = date;
    }
  },
  actions: {
    async getProcessInstances(context: ActionContext<ProcessInstancesState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      //const processInstances = await ProcessService.loadMyInstances();
      const cfg = FetchUtils.getAxiosConfig(FetchUtils.getGETConfig());

      try {
        const res = await ServiceInstanceControllerApiFactory(cfg).getAssignedInstances();

        context.commit('setLastFetch', new Date().getTime());
        context.commit('setProcessInstances', res.data);
      } catch (error) {
        FetchUtils.defaultCatchHandler(error, "Die Vorgänge konnten nicht geladen werden. Bitte versuchen Sie es erneut.");
      }
    }
  }
};
