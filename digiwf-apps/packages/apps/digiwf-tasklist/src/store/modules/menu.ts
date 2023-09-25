export interface MenuState {
  open: boolean;
}

export default {
  namespaced: true,
  state: {
    open: true
  } as MenuState,
  getters: {
    open(state: MenuState): boolean {
      return state.open;
    }
  },
  mutations: {
    setOpen(state: MenuState, open: boolean): void {
      state.open = open;
    }
  },
};
