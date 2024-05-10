import { accessibility, AccessibilityState } from "./accessibility";

describe("accessibility", () => {
  describe("getters:isHighContrastModeEnabled", () => {
    it("should return current value if high contrast mode is set to false", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: false,
        a11YNotificationEnabled: false,
      };
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeFalsy();
    });
    it("should return current value if high contrast mode is set to true", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: true,
        a11YNotificationEnabled: false,
      };
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeTruthy();
    });
    it("should return default value false if no value is set", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: undefined,
        a11YNotificationEnabled: undefined,
      } as any; // so that we can set value to undefined
      const result = accessibility.getters.isHighContrastModeEnabled(state);
      expect(result).toBeFalsy();
    });
  });
  describe("getters:isA11YNotificationEnabled", () => {
    it("should return current value if a11 notification is set to false", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: false,
        a11YNotificationEnabled: false,
      };
      const result = accessibility.getters.isA11YNotificationEnabled(state);
      expect(result).toBeFalsy();
    });
    it("should return current value if a11 notification is set to true", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: false,
        a11YNotificationEnabled: true,
      };
      const result = accessibility.getters.isA11YNotificationEnabled(state);
      expect(result).toBeTruthy();
    });
    it("should return default value false if no value is set", () => {
      const state: AccessibilityState = {
        highContrastModeEnabled: undefined,
        a11YNotificationEnabled: undefined,
      } as any; // so that we can set value to undefined
      const result = accessibility.getters.isA11YNotificationEnabled(state);
      expect(result).toBeFalsy();
    });
  });
});
