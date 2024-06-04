import { accessibility, AccessibilityState } from "./accessibility";

describe("accessibility", () => {
  describe("getters:isHighContrastModeEnabled", () => {
    it("should return current value if high contrast mode is set to false", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: false,
        a11YScreenreaderModeEnabled: false,
      };
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeFalsy();
    });
    it("should return current value if high contrast mode is set to true", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: true,
        a11YScreenreaderModeEnabled: false,
      };
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeTruthy();
    });
    it("should return default value false if no value is set", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: undefined,
        a11YScreenreaderModeEnabled: undefined,
      } as any; // so that we can set value to undefined
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeFalsy();
    });
  });
  describe("getters:isA11YScreenreaderModeEnabled", () => {
    it("should return current value if a11 notification is set to false", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: false,
        a11YScreenreaderModeEnabled: false,
      };
      const result = accessibility.getters.isA11YScreenreaderModeEnabled(state);
      expect(result).toBeFalsy();
    });
    it("should return current value if a11 notification is set to true", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: false,
        a11YScreenreaderModeEnabled: true,
      };
      const result = accessibility.getters.isA11YScreenreaderModeEnabled(state);
      expect(result).toBeTruthy();
    });
    it("should return default value false if no value is set", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: undefined,
        a11YScreenreaderModeEnabled: undefined,
      } as any; // so that we can set value to undefined
      const result = accessibility.getters.isA11YScreenreaderModeEnabled(state);
      expect(result).toBeFalsy();
    });
  });
});
