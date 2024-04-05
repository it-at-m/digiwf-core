import { FormBuilderSettings } from "../../types";
import { containerSchemaBuilder } from "../base/Container";
import { genericSchema } from "../base/FormFields";
import {
  containerIconMap,
  defaultContainerIcon,
  defaultIcon,
  iconMap,
} from "../base/IconMap";
import { Labels } from "../base/labels";
import {
  containerPaletteBuilder,
  formFieldPaletteBuilder,
  objectPaletteBuilder,
  optionalItemBuilder,
  optionalObjectPaletteBuilder,
  optionalPropertiesBuilder,
  sectionPaletteBuilder,
} from "../base/ModelerPalette";
import { sectionBuilder } from "../base/Section";
import { schemaMap } from "./FormFields";

const deLabels: Labels = {
  title: "Titel",
  description: "Beschreibung",
  key: "Schlüssel",
  ui: "UI",
  general: "Allgemein",
  section: "Sektion",
  optionalObject: "Optionales Objekt",
  optionalSelectItem: "Optionales Auswahl-Item",
  optionalGroup: "Optionale Gruppe",
  optionalFields: "Optionale Felder",
  markdown: "Markdown",
  list: "Liste",
  switch: "Schalter",
  dmsInput: "DMS Eingabe",
  multiUserInput: "Multi-Benutzer Eingabe",
  userInput: "Benutzereingabe",
  files: "Dateien",
  select: "Auswählen",
  multiSelect: "Mehrfachauswahl",
  time: "Zeit",
  date: "Datum",
  checkbox: "Checkbox",
  floatNumber: "Gleitkommazahl",
  number: "Zahl",
  textarea: "Textfeld",
  text: "Textfeld",
  options: "Optionen",
  dynamicObject: "Dynamisches Objekt",
  dynamicObjectList: "Dynamische Objektliste",
  containerDescription:
    "Ich bin eine Beschreibung, die als Absatz oben in der Sektion angezeigt wird",
  maxColSize: "Größe (max 12)",
  defaultColSize: "Standardgröße",
  colSizeSmallDevices: "Größe auf kleinen Geräten",
  default: "Standard",
  messages: "Nachrichten",
  validation: "Validierung",
  type: "Typ",
  errorMsgPattern: "Fehlermeldung im Muster",
  errorMsgMinString: "Mindestens {minLength} Zeichen",
  errorMsgMaxString: "Maximal {maxLength} Zeichen",
  errorMsgMinNumber: "Mindestens {minimum}",
  errorMsgMaxNumber: "Maximal {maximum}",
  errorMsgMinArray: "Mindestens {minItems} Einträge",
  errorMsgMaxArray: "Maximal {maxItems} Einträge",
  validationMin: "Minimum",
  validationMax: "Maximum",
  validationAdditionalRules: "Zusätzliche Regeln",
  dmsSystem: "DMS System",
  dmsObjectClass: "Objektklasse",
  dmsDefaultDescription: "List of COOs or links to be checked by the input",
};

const Settings: FormBuilderSettings = {
  iconSettings: {
    iconMap: iconMap,
    defaultIcon: defaultIcon,
    containerIconMap: containerIconMap,
    defaultContainerIcon: defaultContainerIcon,
  },
  modelerPalette: {
    formFields: formFieldPaletteBuilder(deLabels),
    containers: containerPaletteBuilder(deLabels),
    objects: objectPaletteBuilder(deLabels),
    sections: sectionPaletteBuilder(deLabels),
    optionalObject: optionalObjectPaletteBuilder(deLabels),
    optionalProperties: optionalPropertiesBuilder(deLabels),
    optionalItem: optionalItemBuilder(deLabels),
  },
  containerSchema: containerSchemaBuilder("container", deLabels),
  conditionalContainerSchema: containerSchemaBuilder(
    "conditionalContainer",
    deLabels
  ),
  conditionalObjectContainerSchema: containerSchemaBuilder(
    "conditionalObjectContainer",
    deLabels
  ),
  sectionSchema: sectionBuilder(deLabels),
  defaultFormFieldSchema: genericSchema(deLabels),
  formFieldSchemas: schemaMap(deLabels),
};

export default Settings;
