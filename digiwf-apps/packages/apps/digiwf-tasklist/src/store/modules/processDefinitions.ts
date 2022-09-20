import {ActionContext} from "vuex";
import {RootState} from "@/store";
import {TasksState} from "@/store/modules/tasks";
import {ServiceDefinitionControllerApiFactory, ServiceDefinitionTO} from '@/api/api-client/api';
import FetchUtils from "@/api/FetchUtils";

export interface ProcessDefinitionState {
  processDefinitions: ServiceDefinitionTO[];
  lastFetch: number;
}

export default {
  namespaced: true,
  state: {
    processDefinitions: new Array<ServiceDefinitionTO>(),
    lastFetch: 0
  } as ProcessDefinitionState,
  getters: {
    shouldUpdate: (state: TasksState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    processDefinitions(state: ProcessDefinitionState): ServiceDefinitionTO[] {
      return state.processDefinitions
        .map(obj => ({...obj, name: obj.name ?? "Unbekannt"}))
        .filter(Boolean).sort((a, b) => a.name.localeCompare(b.name));
    }
  },
  mutations: {
    setProcessDefinitions(state: ProcessDefinitionState, processDefinitions: ServiceDefinitionTO[]): void {
      state.processDefinitions = processDefinitions;
    },
    setLastFetch(state: TasksState, date: number): void {
      state.lastFetch = date;
    }
  },
  actions: {
    async loadProcessDefinitions(context: ActionContext<ProcessDefinitionState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      //const processDefinitions = await ProcessService.loadProcesses();
      const cfg = FetchUtils.getAxiosConfig(FetchUtils.getGETConfig());

      try {
        const res = await ServiceDefinitionControllerApiFactory(cfg).getServiceDefinitions();

        context.commit('setLastFetch', new Date().getTime());
        context.commit('setProcessDefinitions', res.data);
      } catch (error) {
        FetchUtils.defaultCatchHandler(error, "Die Vorgänge konnten nicht geladen werden. Bitte versuchen Sie es erneut.");
      }
    }
  }
};
