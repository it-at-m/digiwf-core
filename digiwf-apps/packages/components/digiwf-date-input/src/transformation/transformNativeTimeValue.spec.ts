import {transformNativeTimeValue} from "./transformNativeTimeValue";

describe("transformNativeTimeValue", () => {
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
    expect(result).toBeUndefined()
  });
});
