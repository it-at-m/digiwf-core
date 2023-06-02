import {dateToIsoDateTime, formatIsoDate, formatIsoDateTime} from "./time";

describe("time", () => {
  describe("formatIsoDateTime", () => {
    it("should format IsoDateTime correctly", () => {
      const result = formatIsoDateTime("2009-01-02T12:30:00+01:00");
      expect(result).toBe("02.01.2009, 12:30");
    })
  })

  describe("formatIsoDate", () => {
    it("should format IsoDateTime correctly", () => {
      const result = formatIsoDate("2009-01-02T12:00:00+01:00");
      expect(result).toBe("02.01.2009");
    })
  })

  describe("dateToIsoDateTime", () => {
    it("should transform time correctly", () => {
      const result = dateToIsoDateTime("2023.05.23");
      console.log("result")
      expect(result).toBe("2023-05-23T00:00:00.000+02:00");
    })
  })
})

