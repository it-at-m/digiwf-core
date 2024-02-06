export function nullToUndefined<T = unknown>(variable?: T | null): T | undefined {
  return variable || undefined;
}

export const filterInputsWithValue = (value: any): any => {
  const keys = Object.keys(value);
  const newObject: any = {};
  keys.forEach(it => {
    const v = value[it];
    const hasValue = v !== null && v !== undefined;
    if (hasValue && v.toString().trim().length > 0) {
      newObject[it] = v;
    }
  });
  return newObject;
};
