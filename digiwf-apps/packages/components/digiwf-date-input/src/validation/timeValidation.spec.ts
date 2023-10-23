import {validateTime, ERROR_MESSAGES} from "./timeValidation";

describe("validateTime", () => {
  it("should return true if time is valid", () => {
    expect(validateTime("23:59", true)).toBe(true)
    expect(validateTime("00:00", )).toBe(true)
    expect(validateTime("24:00", )).toBe(true)
    expect(validateTime("12:35", )).toBe(true)
  });
  it("should return message if date format is incorrect", () => {
    expect(validateTime("2222:22")).toBe(ERROR_MESSAGES.INVALID)
  });
  it("should return message if native element validity state is false", () => {
    expect(validateTime("", false)).toBe(ERROR_MESSAGES.INVALID)
  });
  it("should return message if hours or minutes are to high", () => {
    expect(validateTime("20:60")).toBe( ERROR_MESSAGES.MINUTES_TO_HIGH)
    expect(validateTime("25:00")).toBe( ERROR_MESSAGES.HOURS_TO_HIGH)
    expect(validateTime("24:01")).toBe( ERROR_MESSAGES.HOURS_TO_HIGH)
  });
  it("should return true if date is empty", () => {
    expect(validateTime("")).toBe( true)
    expect(validateTime("", true)).toBe( true)
  });
});
