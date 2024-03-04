import { Labels } from "@/base/labels";
import { FormBuilderSettings } from "../../types";
import { containerIconMap, defaultContainerIcon, defaultIcon, iconMap } from "../base/IconMap";
import {
  containerPaletteBuilder,
  formFieldPaletteBuilder,
  objectPaletteBuilder,
  optionalItemBuilder,
  optionalObjectPaletteBuilder,
  optionalPropertiesBuilder,
  sectionPaletteBuilder
} from "../base/ModelerPalette";
import { genericSchema } from "../base/FormFields";
import { sectionBuilder } from "../base/Section";
import { containerSchemaBuilder } from "../base/Container";
import { schemaMap } from "./FormFields";

export const enLabels: Labels = {
  title: "Title",
  description: "Description",
  key: "Key",
  ui: "UI",
  general: "General",
  section: "Section",
  optionalObject: "Optional Object",
  optionalSelectItem: "Optional Select Item",
  optionalGroup: "Optional Group",
  optionalFields: "Optional Fields",
  markdown: "Markdown",
  list: "List",
  switch: "Switch",
  dmsInput: "DMS Input",
  multiUserInput: "Multi User Input",
  userInput: "User Input",
  files: "Files",
  select: "Select",
  multiSelect: "Multi Select",
  time: "Time",
  date: "Date",
  checkbox: "Checkbox",
  floatNumber: "Floating Number",
  number: "Number",
  textarea: "Textarea",
  text: "Textfield",
  options: "Options",
  dynamicObject: "Dynamic Object",
  dynamicObjectList: "Dynamic Object List",
  containerDescription: "I'm a description shown as a paragraph on top of section",
  maxColSize: "Size (max 12)",
  defaultColSize: "Default size",
  colSizeSmallDevices: "Size on small devices",
  default: "Default",
  messages: "Messages",
  validation: "Validation",
  type: "Type",
  errorMsgPattern: "Pattern error message",
  errorMsgMinString: "Minimum {minLength} characters",
  errorMsgMaxString: "Maximum {maxLength} characters",
  errorMsgMinNumber: "Minimum {minimum}",
  errorMsgMaxNumber: "Maximum {maximum}",
  errorMsgMinArray: "Minimum {minItems} entries",
  errorMsgMaxArray: "Maximum {maxItems} entries",
  validationMin: "Minimum",
  validationMax: "Maximum",
  validationAdditionalRules: "Additional rules",
  dmsSystem: "DMS System",
  dmsObjectClass: "Objectclass",
  dmsDefaultDescription: "List of COOs or links to be checked by the input",
}


const Settings: FormBuilderSettings = {
    iconSettings: {
        containerIconMap: containerIconMap,
        iconMap: iconMap,
        defaultIcon: defaultIcon,
        defaultContainerIcon: defaultContainerIcon
    },
    modelerPalette: {
        formFields: formFieldPaletteBuilder(enLabels),
        containers: containerPaletteBuilder(enLabels),
        objects: objectPaletteBuilder(enLabels),
        sections: sectionPaletteBuilder(enLabels),
        optionalObject: optionalObjectPaletteBuilder(enLabels),
        optionalProperties: optionalPropertiesBuilder(enLabels),
        optionalItem: optionalItemBuilder(enLabels)
    },
    containerSchema: containerSchemaBuilder("container", enLabels),
    conditionalContainerSchema: containerSchemaBuilder("conditionalContainer", enLabels),
    conditionalObjectContainerSchema: containerSchemaBuilder("conditionalObjectContainer", enLabels),
    sectionSchema: sectionBuilder(enLabels),
    defaultFormFieldSchema: genericSchema(enLabels),
    formFieldSchemas: schemaMap(enLabels)
}

export default Settings;
