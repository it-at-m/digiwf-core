import {ActionContext} from "vuex";
import {RootState} from "../index";
import {FetchUtils, HumanTaskRestControllerApiFactory, HumanTaskTO} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

export interface TasksState {
  tasks: HumanTaskTO[];
  followUp: boolean;
  lastFetch: number | null;
  assignedGroupTasksFilter: string;
  openGroupTasksFilter: string;
  tasksFilter: string;
}

export default {
  namespaced: true,
  state: {
    tasks: new Array<HumanTaskTO>(),
    followUp: false,
    lastFetch: null,
    assignedGroupTasksFilter: "",
    openGroupTasksFilter: "",
    tasksFilter: ""
  } as TasksState,
  getters: {
    shouldUpdate: (state: TasksState) => (): boolean => {
      const lastFetch = state.lastFetch;
      if (!lastFetch) {
        return true;
      }
      const currentTimeStamp = new Date().getTime();
      return (currentTimeStamp - lastFetch) / 1000 > 60;
    },
    tasks(state: TasksState): HumanTaskTO[] {
      return state.tasks.filter(Boolean).sort((a, b) => b.creationTime!.localeCompare(a.creationTime!));
    },
    followUp(state: TasksState): boolean {
      return state.followUp;
    },
    assignedGroupTasksFilter(state: TasksState): string | undefined {
      return state.assignedGroupTasksFilter;
    },
    openGroupTasksFilter(state: TasksState): string | undefined {
      return state.openGroupTasksFilter;
    },
    tasksFilter(state: TasksState): string | undefined {
      return state.tasksFilter;
    }
  },
  mutations: {
    setTasks(state: TasksState, tasks: HumanTaskTO[]): void {
      state.tasks = tasks;
    },
    setLastFetch(state: TasksState): void {
      state.lastFetch = new Date().getTime();
    },
    setFollowUp(state: TasksState, followUp: boolean): void {
      state.followUp = followUp;
    },
    setAssignedGroupTasksFilter(state: TasksState, filter: string): void {
      state.assignedGroupTasksFilter = filter;
    },
    setOpenGroupTasksFilter(state: TasksState, filter: string): void {
      state.openGroupTasksFilter = filter;
    },
    setTasksFilter(state: TasksState, filter: string): void {
      state.tasksFilter = filter;
    }
  },
  actions: {
    async getTasks(context: ActionContext<TasksState, RootState>, forceRefresh: boolean): Promise<void> {
      if (!forceRefresh && !context.getters.shouldUpdate()) {
        return;
      }
      //const tasks = await TaskService.getMyTasks();

      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());

      try {

        const res = await HumanTaskRestControllerApiFactory(cfg).getTasks();

        context.commit('setTasks', res.data);
        context.commit('setLastFetch');
      } catch (err) {
        FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.");
      }

    },
    setFollowUp(context: ActionContext<TasksState, RootState>, followUp: boolean): void {
      context.commit('setFollowUp', followUp);
    }
  }
};
