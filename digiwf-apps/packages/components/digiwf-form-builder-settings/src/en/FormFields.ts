import { Labels, schemaBuilder } from "../utils";

export const textFeldSchema = (labels) => {
  const schema = schemaBuilder("string", labels);
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

export const textAreaSchema = (labels) => {
  const schema = schemaBuilder("string", labels);
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

export const integerSchema = (labels) => {
  const schema = schemaBuilder("number", labels);
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

export const markdownSchema = (labels) => {
  const schema = schemaBuilder("markdown", labels);
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

export const switchSchema = (labels) => {
  const schema = schemaBuilder("boolean", labels);
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

export const dateSchema = (labels) => {
  const schema = schemaBuilder("date", labels);
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

export const constSchema = {
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

export const objectInput = (labels) => {
  const schema = schemaBuilder("object", labels);
  (schema.allOf[0].properties as any)["additionalProperties"] = {
    "const": false,
  };
  return schema;
};

export const timeSchema = (labels) => {
  const schema = schemaBuilder("time", labels);
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

export const checkboxSchema = (labels) => {
  const schema = schemaBuilder("boolean", labels);
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

export const selectSchema = (labels) => {
  const schema = schemaBuilder("select", labels);
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

export const multiselectSchema = (labels) => {
  const schema = schemaBuilder("array", labels);
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

export const fileSchema = (labels: Labels) => {
  const schema = schemaBuilder("file", labels);
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

export const userinputSchema = (labels) => {
  const schema = schemaBuilder("user", labels);
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

export const multiUserinputSchema = (labels) => {
  const schema = schemaBuilder("array", labels);
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

export const arrayInput = (labels) => {
  const schema = schemaBuilder("array", labels);
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

export const arrayObjectInput = (labels) => {
  const schema = schemaBuilder("array", labels);
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

export const genericSchema = (labels: Labels) => {
  const schema = schemaBuilder("generic", labels);
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

export const enLabels: Labels = {
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
