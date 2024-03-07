import { mergeObjects } from "./mergeObjects";

describe("mergeObjects", () => {
  it("should merge keys of both objects", () => {
    const primaryObject = {
      a: "a",
      c: "c",
      d: "d",
    };
    const secondaryObject = {
      b: "b",
    };

    const result = mergeObjects(primaryObject, secondaryObject);
    expect(result).toStrictEqual({
      a: "a",
      b: "b",
      c: "c",
      d: "d",
    });
  });
  it("should prefer value of first object", () => {
    const primaryObject = {
      a: "a",
      c: "c",
      d: "d",
    };
    const secondaryObject = {
      a: "another a",
      b: "b",
      c: "another c",
    };

    const result = mergeObjects(primaryObject, secondaryObject);
    expect(result).toStrictEqual({
      a: "a",
      b: "b",
      c: "c",
      d: "d",
    });
  });
  it("should ignore empty strings in primary object", () => {
    const primaryObject = {
      a: " ",
      c: "c",
      d: "d",
    };
    const secondaryObject = {
      a: "another a",
      b: "b",
      c: "another c",
    };

    const result = mergeObjects(primaryObject, secondaryObject);
    expect(result).toStrictEqual({
      a: "another a",
      b: "b",
      c: "c",
      d: "d",
    });
  });
});
