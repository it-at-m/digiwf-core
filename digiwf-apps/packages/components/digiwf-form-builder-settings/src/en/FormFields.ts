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
import { Labels } from "@/base/labels";

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

export const schemaMap = (enLabels: Labels): any => {
  return {
    "textarea": textAreaSchema(enLabels),
    "text": textFeldSchema(enLabels),
    "integer": integerSchema(enLabels),
    "number": integerSchema(enLabels),
    "date": dateSchema(enLabels),
    "time": timeSchema(enLabels),
    "boolean": checkboxSchema(enLabels),
    "select": selectSchema(enLabels),
    "multiselect": multiselectSchema(enLabels),
    "file": fileSchema(enLabels),
    "user-input": userinputSchema(enLabels),
    "multi-user-input": multiUserinputSchema(enLabels),
    "dms-input": dmsInputSchema(enLabels),
    "array": arrayInput(enLabels),
    "arrayObject": arrayObjectInput(enLabels),
    "switch": switchSchema(enLabels),
    "markdown": markdownSchema(enLabels),
    "const": constSchema,
    "object": objectInput(enLabels),
    "objectType": objectInput(enLabels)
  }
};
