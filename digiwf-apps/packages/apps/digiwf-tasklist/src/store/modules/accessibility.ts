import {useStore} from "../../hooks/store";

export interface AccessibilityState {
  highContrastModeEnabled: boolean;
}

const defaultAccessibilityState: AccessibilityState = {
  highContrastModeEnabled: false,
};

export const accessibility = {
  namespaced: true,
  state:defaultAccessibilityState,
  getters: {
    isHighContrastModeEnabled: (state: AccessibilityState): boolean => {
      return state.highContrastModeEnabled !== undefined
        ? state.highContrastModeEnabled
        : state.highContrastModeEnabled;
    }
  },
  mutations: {
    setHighContrastModeEnabled: (state: AccessibilityState, enabled: boolean) => state.highContrastModeEnabled = enabled,
  }
};
export const useAccessibility = () => {
  const store = useStore();
  return {
    isHighContrastModeEnabled: () => store.getters["accessibility/isHighContrastModeEnabled"],
    setHighContrastModeEnabled: (enabled: boolean) => store.commit("accessibility/setHighContrastModeEnabled", enabled),
  };
};
