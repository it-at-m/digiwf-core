export const transformNativeTimeValue = (value?: string) => {
  if(value === undefined) {
    return undefined;
  }
  return value.trim().length === 0
    ? ""
    : `${value}:00Z`;
};
