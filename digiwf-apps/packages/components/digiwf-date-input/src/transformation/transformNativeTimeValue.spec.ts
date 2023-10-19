import {transformNativeTimeValue, transformToNativeTimeValue} from "./transformNativeTimeValue";

describe("transformNativeTimeValue", () => {
  describe("transformFromNativeTimeValue", () => {
    it("should add timezone and seconds if time is given", () => {
      const result = transformNativeTimeValue("12:23");
      expect(result).toBe("12:23:00Z");
    });

    it("should return empty string when the given value is empty", () => {
      const result = transformNativeTimeValue("");
      expect(result).toBe("");

    });
    it("should return undefined when the given value is undefined", () => {
      const result = transformNativeTimeValue(undefined);
      expect(result).toBeUndefined();
    });
    it("should return undefined when the given value is null", () => {
      const result = transformNativeTimeValue(undefined);
      expect(result).toBeUndefined();
    });
  });

  describe("transformToNativeTimeValue", () => {
    it("should add timezone and seconds if time is given", () => {
      const result = transformToNativeTimeValue("12:23:00Z");
      expect(result).toBe("12:23");
    });

    it("should return empty string when the given value is empty", () => {
      const result = transformToNativeTimeValue("");
      expect(result).toBe("");

    });
    it("should return undefined when the given value is undefined", () => {
      const result = transformToNativeTimeValue(undefined);
      expect(result).toBeUndefined();
    });

    it("should return undefined when the given value is null", () => {
      const result = transformToNativeTimeValue(null);
      expect(result).toBeUndefined();
    });
  });
});
