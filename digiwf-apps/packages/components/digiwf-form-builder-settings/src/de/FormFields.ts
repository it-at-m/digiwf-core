import {
  arrayInput,
  arrayObjectInput,
  checkboxSchema,
  dateSchema,
  dmsInputSchema,
  fileSchema,
  integerSchema,
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
import { Labels } from "../base/labels";

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

export const schemaMap = (deLabels: Labels): any => {
  return {
    "textarea": textAreaSchema(deLabels),
    "text": textFeldSchema(deLabels),
    "integer": integerSchema(deLabels),
    "number": integerSchema(deLabels),
    "date": dateSchema(deLabels),
    "time": timeSchema(deLabels),
    "boolean": checkboxSchema(deLabels),
    "select": selectSchema(deLabels),
    "multiselect": multiselectSchema(deLabels),
    "file": fileSchema(deLabels),
    "user-input": userinputSchema(deLabels),
    "multi-user-input": multiUserinputSchema(deLabels),
    "dms-input": dmsInputSchema(deLabels),
    "array": arrayInput(deLabels),
    "arrayObject": arrayObjectInput(deLabels),
    "switch": switchSchema(deLabels),
    "markdown": markdownSchema(deLabels),
    "const": constSchema,
    "object": objectInput(deLabels),
    "objectType": objectInput(deLabels),
  }
}
