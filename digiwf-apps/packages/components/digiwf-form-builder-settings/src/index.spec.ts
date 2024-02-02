import { describe, expect, test } from '@jest/globals';

import SettingsDE from './de/Settings';
import SettingsEN from './en/Settings';
import { schemaMap as enSchemaMap } from "./en/FormFields";
import { schemaMap as deSchemaMap } from "./de/FormFields";

describe("digiwf-form-builder-settings", () => {
  test("Form Fields: settings for english and german should have to same keys", () => {
    const enSchemas = enSchemaMap;
    const deSchemas = deSchemaMap;

    expect(Object.keys(enSchemas)).toEqual(Object.keys(deSchemas));
    Object.keys(enSchemas).forEach(key => {
      const en = flattenObjectKeys(enSchemas[key]);
      const de = flattenObjectKeys(deSchemas[key]);
      expect(en).toEqual(de);
    });
  })
  test("Settings: settings for english and german should have to same keys", () => {
    const enKeys = flattenObjectKeys(SettingsEN);
    const deKeys = flattenObjectKeys(SettingsDE);

    expect(enKeys).toEqual(deKeys)
  })
})

// https://stackoverflow.com/a/56253298
const flattenObjectKeys = (obj: any) => Object.keys(flattenObjectWithParent(obj, undefined, undefined)).sort();
const flattenObjectWithParent = (obj: any, parent: any, res: any = {}) =>{
  for(let key in obj){
    let propName = parent ? parent + '.' + key : key;
    if(typeof obj[key] == 'object'){
      flattenObjectWithParent(obj[key], propName, res);
    } else {
      res[propName] = obj[key];
    }
  }
  return res;
}
