export interface TasksState {
  assignedGroupTasksFilter: string;
  openGroupTasksFilter: string;
}

export default {
  namespaced: true,
  state: {
    assignedGroupTasksFilter: "",
    openGroupTasksFilter: "",
  } as TasksState,
  getters: {
    assignedGroupTasksFilter(state: TasksState): string | undefined {
      return state.assignedGroupTasksFilter;
    },
    openGroupTasksFilter(state: TasksState): string | undefined {
      return state.openGroupTasksFilter;
    },
  },
  mutations: {
    setAssignedGroupTasksFilter(state: TasksState, filter: string): void {
      state.assignedGroupTasksFilter = filter;
    },
    setOpenGroupTasksFilter(state: TasksState, filter: string): void {
      state.openGroupTasksFilter = filter;
    },
  },
};
