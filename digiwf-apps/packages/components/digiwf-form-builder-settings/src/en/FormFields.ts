import { Labels, schemaBuilder } from "../utils";

const enLabels: Labels = {
  sectionTitle: "General",
  sectionDescription: "Description",
  optionsTitle: "Options",
  maxColSize: "Size (max 12)",
  defaultColSize: "Default size",
  colSizeSmallDevices: "Size on small devices",
  errorMsgPattern: "Pattern error message",
  errorMsgMinString: "Minimum {minLength} characters",
  errorMsgMaxString: "Maximum {maxLength} characters",
  errorMsgMinNumber: "Minimum {minimum}",
  errorMsgMaxNumber: "Maximum {maximum}",
  errorMsgMinArray: "Minimum {minItems} entries",
  errorMsgMaxArray: "Maximum {maxItems} entries",
  errorMsgMinTime: "Minimum {minimum}",
  errorMsgMaxTime: "Maximum {maximum}",
  validationMin: "Minimum",
  validationMax: "Maximum",
  validationAdditionalRules: "Additional rules",
};

const textFeldSchema = () => {
  const schema = schemaBuilder("string", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "string",
    "title": "Default",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  return schema;
}

const textAreaSchema = () => {
  const schema = schemaBuilder("string", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "string",
    "title": "Default",
    "x-display": "textarea",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    "const": "textarea"
  };
  return schema;
}

const integerSchema = () => {
  const schema = schemaBuilder("number", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "integer",
    "title": "Default",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  return schema;
};

const markdownSchema = () => {
  const schema = schemaBuilder("markdown", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "string",
    "title": "Default",
    "x-display": "markdown",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    "const": "markdown"
  };
  return schema;
};

const switchSchema = () => {
  const schema = schemaBuilder("boolean", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "boolean",
    "title": "Default",
    "default": false,
    "x-display": "switch",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    "const": "switch"
  };
  return schema;
};

const dateSchema = () => {
  const schema = schemaBuilder("date", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "string",
    "format": "date",
    "title": "Default",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    "const": "custom-date-input"
  };
  (schema.allOf[0].properties as any)["format"] = {
    "const": "date"
  };
  return schema;
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

const objectInput = () => {
  const schema = schemaBuilder("object", enLabels);
  (schema.allOf[0].properties as any)["additionalProperties"] = {
    "const": false,
  };
  return schema;
};

const timeSchema = () => {
  const schema = schemaBuilder("time", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "string",
    "format": "time",
    "title": "Default",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    "const": "custom-time-input"
  };
  (schema.allOf[0].properties as any)["format"] = {
    "const": "time"
  };
  (schema.allOf[1].properties as any)["x-options"].properties["timePickerProps"] = {
    "type": "object",
    "properties": {
      "format": {
        "const": "24hr"
      }
    }
  };
  return schema;
};

const checkboxSchema = () => {
  const schema = schemaBuilder("boolean", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "boolean",
    "title": "Default",
    "default": false,
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    "type": "string",
    "title": "Display",
    "x-props": {
      "outlined": true,
      "dense": true
    },
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 6
      }
    }
  };
  return schema;
};

const selectSchema = () => {
  const schema = schemaBuilder("select", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "string",
    "title": "Default",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    "type": "string",
    "title": "Display",
    "enum": [
      "radio",
      "select"
    ],
    "x-props": {
      "outlined": true,
      "dense": true
    },
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 6
      }
    }
  };
  schema.allOf.splice(1, 0, {
    "title": "Select",
    "type": "object",
    "properties": {
      "anyOf": {
        "type": "array",
        "title": "Entries",
        "x-itemTitle": "title",
        "items": {
          "type": "object",
          "properties": {
            "title": {
              "type": "string",
              "title": "Titel",
              "x-rules": [
                "required"
              ]
            },
            "const": {
              "type": "string",
              "title": "Value",
              "x-rules": [
                "required"
              ]
            }
          }
        }
      }
    }
  } as any);
  return schema;
};

const multiselectSchema = () => {
  const schema = schemaBuilder("array", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "array",
    "title": "default",
    "items": {
      "type": "string"
    },
    "x-props": {
      "outlined": true,
    },
    "x-rules": [
      "required"
    ],
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 12
      }
    }
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    "type": "string",
    "title": "Display",
    "enum": [
      "checkbox",
      "select",
      "switch"
    ],
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 6
      }
    },
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  schema.allOf.splice(1, 0, {
    "title": "Auswahl",
    "type": "object",
    "properties": {
      "items": {
        "type": "object",
        "properties": {
          "type": {
            "const": "string"
          },
          "anyOf": {
            "type": "array",
            "title": "Einträge",
            "x-itemTitle": "title",
            "items": {
              "type": "object",
              "properties": {
                "title": {
                  "type": "string",
                  "title": "Titel",
                  "x-rules": [
                    "required"
                  ]
                },
                "const": {
                  "type": "string",
                  "title": "Wert",
                  "x-rules": [
                    "required"
                  ]
                }
              }
            }
          }
        }
      }
    }
  } as any);
  return schema;
};

const fileSchema = () => {
  const schema = schemaBuilder("file", enLabels);
  (schema.allOf[0].properties as any)["x-display"] = {
    "const": "custom-multi-file-input"
  };
  (schema.allOf[0].properties as any)["filePath"] = {
    "type": "string",
    "title": "Filepath",
    "x-props": {
      "outlined": true,
    },
    "x-rules": [],
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 12
      }
    }
  };
  (schema.allOf[0].properties as any)["properties"] = {
    "const": {"key": {"type": "string"}, "amount": {"type": "integer"}}
  };
  (schema.allOf[0].properties as any)["uuidEnabled"] = {
    "type": "boolean",
    "title": "Unique identifier?",
    "description": "Creates an unique, which will add to the the directory path. It should be used in object lists.",

    "default": false,
    "x-props": {
      "outlined": true,
    },
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 12
      }
    }
  };
  (schema.allOf[2].properties as any) = {
    "x-rules": {
      "type": "array",
      "title": "Regeln",
      "items": {
        "type": "string",
        "enum": [
          "requiredObject",
        ]
      },
      "x-display": "checkbox"
    },
    "maxFiles": {
      "type": "integer",
      "title": "Maximum number of files",
      "x-props": {
        "outlined": true,
        "dense": true
      }
    },
    "maxFileSize": {
      "type": "integer",
      "title": "Maximum file size in MB",
      "x-props": {
        "outlined": true,
        "dense": true
      }
    },
    "maxTotalSize": {
      "type": "integer",
      "title": "Maximum total size of all files in MB",
      "x-props": {
        "outlined": true,
        "dense": true
      }
    },
    "accept": {
      "type": "string",
      "title": "Permitted file formats",
      "description": "The file formats must be specified as MIME type and comma-separated.",
      "x-props": {
        "outlined": true,
        "dense": true
      }
    }
  };
  return schema;
};

const userinputSchema = () => {
  const schema = schemaBuilder("user", enLabels);
  (schema.allOf[0].properties as any)["x-display"] = {
    "const": "custom-user-input"
  };
  (schema.allOf[0].properties as any)["ldap-groups"] = {
    "type": "string",
    "title": "Ldap Gruppen",
    "x-props": {
      "outlined": true,
      "dense": true
    },
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 6
      }
    }
  };
  (schema.allOf[0].properties as any)["default"] = {
    "type": "string",
    "title": "Default",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  return schema;
};

const multiUserinputSchema = () => {
  const schema = schemaBuilder("array", enLabels);
  (schema.allOf[0].properties as any)["x-display"] = {
    "const": "custom-multi-user-input"
  };
  (schema.allOf[0].properties as any)["ldap-groups"] = {
    "type": "string",
    "title": "Ldap Gruppen",
    "x-props": {
      "outlined": true,
      "dense": true
    },
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 6
      }
    }
  };
  (schema.allOf[0].properties as any)["items"] = {
    "type": "object",
    "properties": {
      "type": {
        "const": "string"
      }
    }
  };
  (schema.allOf[0].properties as any)["default"] = {
    "type": "array",
    "title": "default",
    "items": {
      "type": "string"
    },
    "x-props": {
      "outlined": true,
    },
    "x-rules": [
      "required"
    ],
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 12
      }
    }
  };
  return schema;
};

const arrayInput = () => {
  const schema = schemaBuilder("array", enLabels);
  (schema.allOf[0].properties as any)["items"] = {
    "type": "object",
    "properties": {
      "type": {
        "type": "string",
        "title": "Typ",
        "enum": [
          "string",
          "integer"
        ],
        "x-props": {
          "outlined": true,
          "dense": true
        }
      }
    }
  };
  (schema.allOf[0].properties as any)["default"] = {
    "type": "array",
    "title": "default",
    "items": {
      "type": "string"
    },
    "x-props": {
      "outlined": true,
    },
    "x-rules": [
      "required"
    ],
    "x-options": {
      "fieldColProps": {
        "cols": 12,
        "sm": 12
      }
    }
  };
  return schema;
};

const arrayObjectInput = () => {
  const schema = schemaBuilder("array", enLabels);
  (schema.allOf[0].properties as any)["items"] = {
    "type": "object",
    "properties": {
      "type": {
        "const": "object"
      },
      "additionalProperties": {
        "const": false,
      },
      "properties": {
        "type": "object"
      }
    }
  };
  return schema;
};

export const genericSchema = () => {
  const schema = schemaBuilder("generic", enLabels);
  (schema.allOf[0].properties as any)["default"] = {
    "type": "string",
    "title": "Default",
    "x-props": {
      "outlined": true,
      "dense": true
    }
  };
  return schema;
};

export const schemaMap: any = {
  "textarea": textAreaSchema(),
  "text": textFeldSchema(),
  "integer": integerSchema(),
  "number": integerSchema(),
  "boolean": checkboxSchema(),
  "multiselect": multiselectSchema(),
  "file": fileSchema(),
  "user-input": userinputSchema(),
  "multi-user-input": multiUserinputSchema(),
  "array": arrayInput(),
  "arrayObject": arrayObjectInput(),
  "switch": switchSchema(),
  "markdown": markdownSchema(),
  "const": constSchema,
  "object": objectInput(),
  "objectType": objectInput(),
  "select": selectSchema(),
  "date": dateSchema(),
  "time": timeSchema(),
};
