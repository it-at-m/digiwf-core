import { describe, expect, test } from '@jest/globals';

import SettingsDE from './de/Settings';
import SettingsEN from './en/Settings';

describe("digiwf-form-builder-settings", () => {

  test("Form Fields: settings for english and german should have to same keys", () => {
    const enSchemas = SettingsEN.formFieldSchemas;
    const deSchemas = SettingsDE.formFieldSchemas;

    expect(Object.keys(enSchemas)).toEqual(Object.keys(deSchemas));
    Object.keys(enSchemas).forEach(key => {
      const en = flattenObjectKeys(enSchemas[key]);
      const de = flattenObjectKeys(deSchemas[key]);
      expect(en).toEqual(de);
    });
  })

  test("Containers: settings for english and german should have to same keys", () => {
    //section schema
    expect(flattenObjectKeys(SettingsEN.sectionSchema))
      .toEqual(flattenObjectKeys(SettingsDE.sectionSchema));
    // container schema
    expect(flattenObjectKeys(SettingsEN.containerSchema))
      .toEqual(flattenObjectKeys(SettingsDE.containerSchema));
    // conditional container schema
    expect(flattenObjectKeys(SettingsEN.conditionalContainerSchema))
      .toEqual(flattenObjectKeys(SettingsDE.conditionalContainerSchema));
    // conditional object container schema
    expect(flattenObjectKeys(SettingsEN.conditionalObjectContainerSchema))
      .toEqual(flattenObjectKeys(SettingsDE.conditionalObjectContainerSchema));
  })

  test("Modeler Palette: settings for english and german should have to same keys", () => {
    const enSchemas = SettingsEN.modelerPalette;
    const deSchemas = SettingsDE.modelerPalette;

    expect(flattenObjectKeys(enSchemas.formFields)).toEqual(flattenObjectKeys(deSchemas.formFields));
    expect(flattenObjectKeys(enSchemas.containers)).toEqual(flattenObjectKeys(deSchemas.containers));
    expect(flattenObjectKeys(enSchemas.objects)).toEqual(flattenObjectKeys(deSchemas.objects));
    expect(flattenObjectKeys(enSchemas.sections)).toEqual(flattenObjectKeys(deSchemas.sections));
    expect(flattenObjectKeys(enSchemas.optionalObject)).toEqual(flattenObjectKeys(deSchemas.optionalObject));
    expect(flattenObjectKeys(enSchemas.optionalProperties)).toEqual(flattenObjectKeys(deSchemas.optionalProperties));
    expect(flattenObjectKeys(enSchemas.optionalItem)).toEqual(flattenObjectKeys(deSchemas.optionalItem));
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
