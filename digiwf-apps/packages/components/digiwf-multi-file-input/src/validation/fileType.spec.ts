import {validateFileType} from "./fileType";

describe("validateFileType",  () => {

  it("should do not validate if accepted string is undefined or empty", () =>  {
    expect(validateFileType("a.txt", undefined)).toBeUndefined()
    expect(validateFileType("a.txt", "")).toBeUndefined()
    expect(validateFileType("a.txt", "    ")).toBeUndefined()
  });

  it("should return undefined if file type is correct", () =>  {
    expect(validateFileType("a.txt", "text/plain")).toBeUndefined()
    expect(validateFileType("a.txt", "image/png,text/plain,application/pdf")).toBeUndefined()
  });

  it("should return error message if file type is not correct", () =>  {
    expect(validateFileType("a.txt", "image/png,application/pdf")).toBeDefined()
  });
});
