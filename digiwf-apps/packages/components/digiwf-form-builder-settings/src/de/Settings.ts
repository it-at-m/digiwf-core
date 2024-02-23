import { containerIconMap, defaultContainerIcon, defaultIcon, iconMap } from "./IconMap";
import { conditionalContainerSchema, conditionalObjectContainerSchema, containerSchema } from "./Container";
import section from "./Section";
import {
  containerPalette,
  formFieldPalette,
  objectPalette,
  optionalItem,
  optionalObject,
  optionalProperties,
  sectionPalette
} from "./ModelerPalette";
import { schemaMap } from "./FormFields";
import { FormBuilderSettings } from "../../types";
import { genericSchema } from "../base/FormFields";
import { Labels } from "../base/labels";

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
  containerDescription: "Ich bin eine Beschreibung, die als Absatz oben in der Sektion angezeigt wird",
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
  dmsObjectClass: "Objectclass",
  dmsSchriftstueck: "Document",
  dmsSachakte: "Factual files",
  dmsVorgang: "Dossier",
  dmsEingang: "Inbox",
  dmsAusgang: "Exit",
  dmsIntern: "Internal",
  dmsDefaultDescription: "List of COOs or links to be checked by the input",
}

const Settings: FormBuilderSettings = {
    iconSettings: {
        iconMap: iconMap,
        defaultIcon: defaultIcon,
        containerIconMap: containerIconMap,
        defaultContainerIcon: defaultContainerIcon
    },
    modelerPalette: {
        formFields: formFieldPalette,
        containers: containerPalette,
        objects: objectPalette,
        sections: sectionPalette,
        optionalObject: optionalObject,
        optionalProperties: optionalProperties,
        optionalItem: optionalItem

    },
    containerSchema: containerSchema,
    conditionalContainerSchema: conditionalContainerSchema,
    conditionalObjectContainerSchema: conditionalObjectContainerSchema,
    sectionSchema: section,
    defaultFormFieldSchema: genericSchema(deLabels),
    formFieldSchemas: schemaMap(deLabels)
};

export default Settings;
