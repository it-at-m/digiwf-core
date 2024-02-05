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


export const enLabels: Labels = {
  sectionTitle: "General",
  sectionDescription: "Description",
  optionsTitle: "Options",
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
};

const constSchema = {
  "title": "General",
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
  "textarea": textAreaSchema(enLabels),
  "text": textFeldSchema(enLabels),
  "integer": integerSchema(enLabels),
  "number": integerSchema(enLabels),
  "boolean": checkboxSchema(enLabels),
  "multiselect": multiselectSchema(enLabels),
  "file": fileSchema(enLabels),
  "user-input": userinputSchema(enLabels),
  "multi-user-input": multiUserinputSchema(enLabels),
  "array": arrayInput(enLabels),
  "arrayObject": arrayObjectInput(enLabels),
  "switch": switchSchema(enLabels),
  "markdown": markdownSchema(enLabels),
  "const": constSchema,
  "object": objectInput(enLabels),
  "objectType": objectInput(enLabels),
  "select": selectSchema(enLabels),
  "date": dateSchema(enLabels),
  "time": timeSchema(enLabels),
};
