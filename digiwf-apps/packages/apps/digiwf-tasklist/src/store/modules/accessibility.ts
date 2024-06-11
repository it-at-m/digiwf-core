import { useStore } from "../../hooks/store";

export interface AccessibilityState {
  highContrastModeEnabled: boolean;
  a11YScreenreaderModeEnabled: boolean;
}

const defaultAccessibilityState: AccessibilityState = {
  highContrastModeEnabled: false,
  a11YScreenreaderModeEnabled: false,
};

export const accessibility = {
  namespaced: true,
  state: defaultAccessibilityState,
  getters: {
    isHighContrastModeEnabled: (state: AccessibilityState): boolean => {
      return state.highContrastModeEnabled !== undefined
        ? state.highContrastModeEnabled
        : defaultAccessibilityState.highContrastModeEnabled;
    },
    isA11YScreenreaderModeEnabled: (state: AccessibilityState): boolean => {
      return state.a11YScreenreaderModeEnabled !== undefined
        ? state.a11YScreenreaderModeEnabled
        : defaultAccessibilityState.a11YScreenreaderModeEnabled;
    },
  },
  mutations: {
    setHighContrastModeEnabled: (state: AccessibilityState, enabled: boolean) =>
      (state.highContrastModeEnabled = enabled),
    setA11YScreenreaderModeEnabled: (
      state: AccessibilityState,
      enabled: boolean
    ) => (state.a11YScreenreaderModeEnabled = enabled),
  },
};

export interface Accessibility {
  isHighContrastModeEnabled: () => boolean;
  setHighContrastModeEnabled: (value: boolean) => void;
  a11YScreenreaderModeEnabled: () => boolean;
  setA11YScreenreaderModeEnabled: (value: boolean) => void;
}

export const useAccessibility = (): Accessibility => {
  const store = useStore();
  return {
    isHighContrastModeEnabled: () =>
      store.getters["accessibility/isHighContrastModeEnabled"],
    setHighContrastModeEnabled: (enabled: boolean) =>
      store.commit("accessibility/setHighContrastModeEnabled", enabled),
    a11YScreenreaderModeEnabled: () =>
      store.getters["accessibility/isA11YScreenreaderModeEnabled"],
    setA11YScreenreaderModeEnabled: (enabled: boolean) =>
      store.commit("accessibility/setA11YScreenreaderModeEnabled", enabled),
  };
};
