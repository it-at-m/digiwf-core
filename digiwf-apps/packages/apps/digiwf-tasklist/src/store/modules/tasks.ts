import {ActionContext} from "vuex";
import {RootState} from "../index";

export interface TasksState {
  followUp: boolean;
  lastFetch: number | null;
  assignedGroupTasksFilter: string;
  openGroupTasksFilter: string;
  tasksFilter: string;
}

export default {
  namespaced: true,
  state: {
    followUp: false,
    lastFetch: null,
    assignedGroupTasksFilter: "",
    openGroupTasksFilter: "",
  } as TasksState,
  getters: {
    followUp(state: TasksState): boolean {
      return state.followUp;
    },
    assignedGroupTasksFilter(state: TasksState): string | undefined {
      return state.assignedGroupTasksFilter;
    },
    openGroupTasksFilter(state: TasksState): string | undefined {
      return state.openGroupTasksFilter;
    },
  },
  mutations: {
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
  },
  actions: {
    setFollowUp(context: ActionContext<TasksState, RootState>, followUp: boolean): void {
      context.commit('setFollowUp', followUp);
    }
  }
};
