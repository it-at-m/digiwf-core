import {
  arrayInput,
  arrayObjectInput,
  checkboxSchema,
  dateSchema,
  fileSchema,
  integerSchema,
  Labels,
  markdownSchema,
  multiselectSchema,
  multiUserinputSchema,
  objectInput,
  selectSchema,
  switchSchema,
  textAreaSchema,
  textFeldSchema,
  timeSchema,
  userinputSchema
} from "../base/FormFields";

export const deLabels: Labels = {
  sectionTitle: "Allgemein",
  sectionDescription: "Beschreibung",
  optionsTitle: "Optionen",
  maxColSize: "Größe (max 12)",
  defaultColSize: "Standardgröße",
  colSizeSmallDevices: "Größe auf kleinen Geräten",
  default: "Standard",
  messages: "Nachrichten",
  validation: "Validierung",
  type: "Typ",
  errorMsgPattern: "Pattern Fehlermeldung",
  errorMsgMinString: "Minimum {minLength} Zeichen",
  errorMsgMaxString: "Maximum {maxLength} Zeichen",
  errorMsgMinNumber: "Minimum {minimum}",
  errorMsgMaxNumber: "Maximum {maximum}",
  errorMsgMinArray: "Minimum {minItems} Einträge",
  errorMsgMaxArray: "Maximum {maxItems} Einträge",
  validationMin: "Minimum",
  validationMax: "Maximum",
  validationAdditionalRules: "Zusätzliche Regeln",
};

const constSchema = {
  "title": "Allgemein",
  "type": "object",
  "properties": {
    "fieldType": {
      "type": "string",
      "title": "Type",
      "readOnly": true
    },
    "type": {
      "type": "string",
      "x-display": "hidden",
    },
    "key": {
      "type": "string",
      "title": "Key",
      "x-props": {
        "outlined": true,
        "dense": true
      },
      "x-rules": [
        "required"
      ]
    },
    "const": {
      "type": "string",
      "title": "Const",
      "x-props": {
        "outlined": true,
        "dense": true
      },
      "x-rules": [
        "required"
      ]
    },
  }
};

export const schemaMap: any = {
  "textarea": textAreaSchema(deLabels),
  "text": textFeldSchema(deLabels),
  "integer": integerSchema(deLabels),
  "number": integerSchema(deLabels),
  "boolean": checkboxSchema(deLabels),
  "multiselect": multiselectSchema(deLabels),
  "file": fileSchema(deLabels),
  "user-input": userinputSchema(deLabels),
  "multi-user-input": multiUserinputSchema(deLabels),
  "array": arrayInput(deLabels),
  "arrayObject": arrayObjectInput(deLabels),
  "switch": switchSchema(deLabels),
  "markdown": markdownSchema(deLabels),
  "const": constSchema,
  "object": objectInput(deLabels),
  "objectType": objectInput(deLabels),
  "select": selectSchema(deLabels),
  "date": dateSchema(deLabels),
  "time": timeSchema(deLabels),
};
