import * as _ from "lodash";

export const mergeObjects = (mainObject: any, secondaryObject: any): any => {
  const result: any = {};
  const keys = _.uniq([...Object.keys(mainObject), ...Object.keys(secondaryObject)]);
  keys.forEach((key: string) => {
    const mainValue = mainObject[key];
    const secondaryValue = secondaryObject[key];
    result[key] = mergeValues(mainValue, secondaryValue);
  });
  return result;
};

const mergeValues = (mainValue?: any, secondaryValue?: any): any | undefined => {
  if (mainValue && _.toString(mainValue).trim().length > 0) {
    return mainValue;
  }
  return secondaryValue;
};
