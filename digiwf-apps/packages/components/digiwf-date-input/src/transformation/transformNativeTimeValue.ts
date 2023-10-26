export const transformNativeTimeValue = (value?: string | null) => {
  if(value === undefined || value === null) {
    return undefined;
  }
  return value.trim().length === 0
    ? ""
    : `${value}:00Z`;
};


export const transformToNativeTimeValue = (value?: string | null) => {
  if(value === undefined || value === null) {
    return undefined;
  }
  return value.trim().length === 0
    ? ""
    : value.trim().substring(0, 5);
}
