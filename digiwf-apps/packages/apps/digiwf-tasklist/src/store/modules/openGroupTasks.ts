import {ActionContext} from "vuex";
import {RootState} from "@/store";
import {TasksState} from "@/store/modules/tasks";
import {HumanTaskRestControllerApiFactory, HumanTaskTO} from '@/api/api-client/api';
import FetchUtils from "@/api/FetchUtils";

export interface OpenGroupTasksState {
  tasks: HumanTaskTO[];
  lastFetch: number;
}

export default {
  namespaced: true,
  state: {
    tasks: new Array<HumanTaskTO>(),
    lastFetch: 0
  } as OpenGroupTasksState,
  getters: {
    shouldUpdate: (state: TasksState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    tasks(state: OpenGroupTasksState): HumanTaskTO[] {
      return state.tasks.filter(Boolean).sort((a, b) => b.creationTime!.localeCompare(a.creationTime!));
    }
  },
  mutations: {
    setTasks(state: OpenGroupTasksState, tasks: HumanTaskTO[]): void {
      state.tasks = tasks;
    },
    setLastFetch(state: TasksState, date: number): void {
      state.lastFetch = date;
    }
  },
  actions: {
    async getTasks(context: ActionContext<OpenGroupTasksState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      // const tasks = await TaskService.getOpenGroupTasks();
      const cfg = FetchUtils.getAxiosConfig(FetchUtils.getGETConfig());
      try {
        const res = await HumanTaskRestControllerApiFactory(cfg).getOpenGroupTasks();
        context.commit('setLastFetch', new Date().getTime());
        context.commit('setTasks', res.data);
      } catch (err) {
        FetchUtils.defaultCatchHandler(err, "Die offenen Gruppenaufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut");
      }

    }
  }
};
