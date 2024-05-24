import { useStore } from "../../hooks/store";

export interface AccessibilityState {
  highContrastModeEnabled: boolean;
  a11YNotificationEnabled: boolean;
}

const defaultAccessibilityState: AccessibilityState = {
  highContrastModeEnabled: false,
  a11YNotificationEnabled: false,
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
    isA11YNotificationEnabled: (state: AccessibilityState): boolean => {
      return state.a11YNotificationEnabled !== undefined
        ? state.a11YNotificationEnabled
        : defaultAccessibilityState.a11YNotificationEnabled;
    },
  },
  mutations: {
    setHighContrastModeEnabled: (state: AccessibilityState, enabled: boolean) =>
      (state.highContrastModeEnabled = enabled),
    setA11YNotificationEnabled: (state: AccessibilityState, enabled: boolean) =>
      (state.a11YNotificationEnabled = enabled),
  },
};

export interface Accessibility {
  isHighContrastModeEnabled: () => boolean;
  setHighContrastModeEnabled: (value: boolean) => void;
  a11YNotificationEnabled: () => boolean;
  setA11YNotificationEnabled: (value: boolean) => void;
}

export const useAccessibility = (): Accessibility => {
  const store = useStore();
  return {
    isHighContrastModeEnabled: () =>
      store.getters["accessibility/isHighContrastModeEnabled"],
    setHighContrastModeEnabled: (enabled: boolean) =>
      store.commit("accessibility/setHighContrastModeEnabled", enabled),
    a11YNotificationEnabled: () =>
      store.getters["accessibility/isA11YNotificationEnabled"],
    setA11YNotificationEnabled: (enabled: boolean) =>
      store.commit("accessibility/setA11YNotificationEnabled", enabled),
  };
};
