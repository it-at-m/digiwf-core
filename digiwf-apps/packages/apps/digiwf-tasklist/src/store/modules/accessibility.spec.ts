import {accessibility, AccessibilityState} from "./accessibility";

describe("accessibility", () => {
  describe("getters:isHighContrastModeEnabled", () => {
    it("should return current value if high contrast mode is set to false", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: false,
      };
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeFalsy();

    });
    it("should return current value if high contrast mode is set to true", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: true,
      };
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeTruthy();
    });
    it("should return default value false if no value is set", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: undefined,
      } as any; // so that we can set value to undefined
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeFalsy();
    });
  });
});
