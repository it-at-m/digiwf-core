import { parseQueryParameterInputs } from "./urlQueryForFormFields";

describe("parseQueryParameterInputs", () => {
  it("should return json object when input is correct", () => {
    expect(parseQueryParameterInputs('{"key":"value"}')).toEqual({
      key: "value",
    });
  });

  it("should return empty object if input is empty or undefined", () => {
    expect(parseQueryParameterInputs()).toEqual({});
    expect(parseQueryParameterInputs("")).toEqual({});
  });

  it("should return empty object when json is invalid", () => {
    expect(parseQueryParameterInputs("invalid json")).toEqual({});
  });
});
