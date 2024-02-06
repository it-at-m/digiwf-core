import {filterInputsWithValue} from "./dataTransformations";

describe("filterInputsWithValue", () => {
  it("should filter entries with no value", () => {
    const inputWithValues = {
      str1: "1",
      str2: "undefined",
      nr1: 1,
      nr2: 0,
      b1: true,
      b2: false,
    }  ;

    const inputWithEmptyValues = {
      nv1: undefined,
      nv2: null,
      nv3: ""
    };
    expect(filterInputsWithValue({...inputWithValues, ...inputWithEmptyValues})).toEqual(inputWithValues);
  });
});
