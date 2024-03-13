import {checkRequired} from "./required";

describe('checkRequired', () => {
  it('returns true if x-rules includes required', () => {
    const schema = {'x-rules': ['required']};
    expect(checkRequired(schema)).toBe(true);
  });


  it('returns true if minLength > 0', () => {
    const schema = {minLength: 1};
    expect(checkRequired(schema)).toBe(true);
  });

  it('returns true if minItems > 0', () => {
    const schema = {minItems: 1};
    expect(checkRequired(schema)).toBe(true);
  });

  it('returns false if none of the conditions are met', () => {
    const schema = {};
    expect(checkRequired(schema)).toBe(false);
  });

  it('returns false if x-rules does not include required', () => {
    const schema = {'x-rules': ['optional']};
    expect(checkRequired(schema)).toBe(false);
  });

  it('returns false if minLength is 0', () => {
    const schema = {minLength: 0};
    expect(checkRequired(schema)).toBe(false);
  });

  it('returns false if minItems is 0', () => {
    const schema = {minItems: 0};
    expect(checkRequired(schema)).toBe(false);
  });
});
