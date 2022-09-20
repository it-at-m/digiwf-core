import {ActionContext} from "vuex";
import {RootState} from "../index";
import {TasksState} from "./tasks";
import {FetchUtils, HumanTaskRestControllerApiFactory, HumanTaskTO} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

export interface AssignedGroupTasksState {
  tasks: HumanTaskTO[];
  lastFetch: number;
}

export default {
  namespaced: true,
  state: {
    tasks: new Array<HumanTaskTO>(),
    lastFetch: 0
  } as AssignedGroupTasksState,
  getters: {
    shouldUpdate: (state: TasksState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    tasks(state: AssignedGroupTasksState): HumanTaskTO[] {
      return state.tasks.filter(Boolean).sort((a, b) => b.creationTime!.localeCompare(a.creationTime!));
    }
  },
  mutations: {
    setTasks(state: AssignedGroupTasksState, tasks: HumanTaskTO[]): void {
      state.tasks = tasks;
    },
    setLastFetch(state: TasksState, date: number): void {
      state.lastFetch = date;
    }
  },
  actions: {
    async getTasks(context: ActionContext<AssignedGroupTasksState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      // const tasks = await TaskService.getAssignedGroupTasks();
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      try {
        const res = await HumanTaskRestControllerApiFactory(cfg).getAssignedGroupTasks();
        context.commit('setLastFetch', new Date().getTime());
        context.commit('setTasks', res.data);
      } catch (err) {
        FetchUtils.defaultResponseHandler(err, "Die Gruppenaufgaben in Bearbeitung konnten nicht geladen werden. Bitte versuchen Sie es erneut.");
      }

    }
  }
};
