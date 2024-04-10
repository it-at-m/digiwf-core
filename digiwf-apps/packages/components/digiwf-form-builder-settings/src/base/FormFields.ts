import {Labels} from "@/base/labels";

export function schemaBuilder(fieldType: string, labels: Labels) {
  return {
    type: "object",
    "x-display": "tabs",
    "x-props": {
      grow: true,
    },
    "x-options": {
      childrenClass: "pr-5 pl-0",
    },
    allOf: [
      {
        title: labels.general,
        type: "object",
        properties: {
          fieldType: {
            type: "string",
            title: "Type",
            readOnly: true,
          },
          type: {
            type: "string",
            "x-display": "hidden",
          },
          key: {
            type: "string",
            title: "Key",
            "x-props": {
              outlined: true,
              dense: true,
            },
            "x-rules": ["required"],
          },
          title: {
            type: "string",
            title: labels.general,
            "x-props": {
              outlined: true,
              dense: true,
            },
            "x-rules": ["required"],
          },
          description: {
            type: "string",
            title: labels.description,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
          readOnly: {
            type: "boolean",
            title: "Readonly",
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
        },
      },
      {
        title: `${labels.options}`,
        type: "object",
        properties: {
          "x-props": {
            type: "object",
            description: "Ui",
            properties: {
              dense: {
                type: "boolean",
                title: "Dense",
                "x-props": {
                  outlined: true,
                  dense: true,
                },
                "x-options": {
                  fieldColProps: {
                    cols: 12,
                    sm: 6,
                  },
                },
              },
              outlined: {
                type: "boolean",
                title: "Outlined",
                "x-props": {
                  outlined: true,
                  dense: true,
                },
                "x-options": {
                  fieldColProps: {
                    cols: 12,
                    sm: 6,
                  },
                },
              },
            },
          },
          "x-options": {
            type: "object",
            properties: {
              fieldColProps: {
                description: `${labels.maxColSize}`,
                type: "object",
                properties: {
                  sm: {
                    type: "integer",
                    title: `${labels.defaultColSize}`,
                    "x-props": {
                      outlined: true,
                      dense: true,
                    },
                    "x-options": {
                      fieldColProps: {
                        cols: 12,
                        sm: 6,
                      },
                    },
                  },
                  cols: {
                    type: "integer",
                    title: `${labels.colSizeSmallDevices}`,
                    "x-props": {
                      outlined: true,
                      dense: true,
                    },
                    "x-options": {
                      fieldColProps: {
                        cols: 12,
                        sm: 6,
                      },
                    },
                  },
                },
              },
              messages: getMessageOptions(fieldType, labels),
            },
          },
        },
      },
      {
        ...getValidationOptions(fieldType, labels),
      },
    ],
  };
}

function getMessageOptions(fieldType: string, labels: Labels) {
  switch (fieldType) {
    case "string":
      return {
        type: "object",
        description: `${labels.messages}`,
        properties: {
          pattern: {
            type: "string",
            title: `${labels.errorMsgPattern}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
          minLength: {
            type: "string",
            title: `${labels.errorMsgMinString}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
          maxLength: {
            type: "string",
            title: `${labels.errorMsgMaxString}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
        },
      };
    case "number":
      return {
        type: "object",
        description: `${labels.messages}`,
        properties: {
          pattern: {
            type: "string",
            title: `${labels.errorMsgPattern}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
          minimum: {
            type: "string",
            title: `${labels.errorMsgMinNumber}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
          maximum: {
            type: "string",
            title: `${labels.errorMsgMaxNumber}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
        },
      };
    case "array":
      return {
        type: "object",
        description: `${labels.messages}`,
        properties: {
          pattern: {
            type: "string",
            title: `${labels.errorMsgPattern}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
          minItems: {
            type: "string",
            title: `${labels.errorMsgMinArray}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
          maxItems: {
            type: "string",
            title: `${labels.errorMsgMaxArray}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
        },
      };
    case "date":
      return {};
    case "time":
      return {
        type: "object",
        description: `${labels.messages}`,
        properties: {
          pattern: {
            type: "string",
            title: `${labels.errorMsgPattern}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
        },
      };
    default:
      return {
        type: "object",
        description: `${labels.messages}`,
        properties: {
          pattern: {
            type: "string",
            title: `${labels.errorMsgPattern}`,
            "x-props": {
              outlined: true,
              dense: true,
            },
          },
        },
      };
  }
}

function getValidationOptions(fieldType: string, labels: Labels) {
  // object has no validation
  if (fieldType === "object") {
    return;
  }

  let additionalValidationOptions = {};
  if (fieldType === "string") {
    additionalValidationOptions = {
      minLength: {
        type: "integer",
        title: `${labels.validationMin}`,
        "x-props": {
          outlined: true,
          dense: true,
        },
      },
      maxLength: {
        type: "integer",
        title: `${labels.validationMax}`,
        "x-props": {
          outlined: true,
          dense: true,
        },
      },
    };
  } else if (fieldType === "number") {
    additionalValidationOptions = {
      minimum: {
        type: "number",
        title: `${labels.validationMin}`,
        "x-props": {
          outlined: true,
          dense: true,
        },
      },
      maximum: {
        type: "number",
        title: `${labels.validationMax}`,
        "x-props": {
          outlined: true,
          dense: true,
        },
      },
    };
  } else if (fieldType === "array") {
    additionalValidationOptions = {
      minItems: {
        type: "integer",
        title: `${labels.validationMin}`,
        "x-props": {
          outlined: true,
          dense: true,
        },
      },
      maxItems: {
        type: "integer",
        title: `${labels.validationMax}`,
        "x-props": {
          outlined: true,
          dense: true,
        },
      },
    };
  }

  return {
    title: `${labels.validation}`,
    type: "object",
    properties: {
      pattern: {
        type: "string",
        title: "Pattern (regex)",
        "x-props": {
          outlined: true,
          dense: true,
        },
      },
      "x-rules": {
        type: "array",
        title: `${labels.validationAdditionalRules}`,
        items: {
          type: "string",
          enum: ["required"],
        },
        "x-display": "checkbox",
      },
      ...additionalValidationOptions,
    },
  };
}

// form fields
export const textFeldSchema = (labels: Labels) => {
  const schema = schemaBuilder("string", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "string",
    title: `${labels.default}`,
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  return schema;
};

export const textAreaSchema = (labels: Labels) => {
  const schema = schemaBuilder("string", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "string",
    title: `${labels.default}`,
    "x-display": "textarea",
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "textarea",
  };
  return schema;
};

export const integerSchema = (labels: Labels) => {
  const schema = schemaBuilder("number", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "integer",
    title: `${labels.default}`,
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  return schema;
};

export const markdownSchema = (labels: Labels) => {
  const schema = schemaBuilder("markdown", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "string",
    title: `${labels.default}`,
    "x-display": "markdown",
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "markdown",
  };
  return schema;
};

export const switchSchema = (labels: Labels) => {
  const schema = schemaBuilder("boolean", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "boolean",
    title: `${labels.default}`,
    default: false,
    "x-display": "switch",
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "switch",
  };
  return schema;
};

export const dateSchema = (labels: Labels) => {
  const schema = schemaBuilder("date", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "string",
    format: "date",
    title: `${labels.default}`,
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "custom-date-input",
  };
  (schema.allOf[0].properties as any)["format"] = {
    const: "date",
  };
  return schema;
};

export const objectInput = (labels: Labels) => {
  const schema = schemaBuilder("object", labels);
  (schema.allOf[0].properties as any)["additionalProperties"] = {
    const: false,
  };
  return schema;
};

export const timeSchema = (labels: Labels) => {
  const schema = schemaBuilder("time", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "string",
    format: "time",
    title: `${labels.default}`,
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "custom-time-input",
  };
  (schema.allOf[0].properties as any)["format"] = {
    const: "time",
  };
  (schema.allOf[1].properties as any)["x-options"].properties[
    "timePickerProps"
    ] = {
    type: "object",
    properties: {
      format: {
        const: "24hr",
      },
    },
  };
  return schema;
};

export const checkboxSchema = (labels: Labels) => {
  const schema = schemaBuilder("boolean", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "boolean",
    title: `${labels.default}`,
    default: false,
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    type: "string",
    title: "Display",
    "x-props": {
      outlined: true,
      dense: true,
    },
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 6,
      },
    },
  };
  return schema;
};

export const selectSchema = (labels: Labels) => {
  const schema = schemaBuilder("select", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "string",
    title: `${labels.default}`,
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    type: "string",
    title: "Display",
    enum: ["radio", "select"],
    "x-props": {
      outlined: true,
      dense: true,
    },
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 6,
      },
    },
  };
  schema.allOf.splice(1, 0, {
    title: "Select",
    type: "object",
    properties: {
      anyOf: {
        type: "array",
        title: "Entries",
        "x-itemTitle": "title",
        items: {
          type: "object",
          properties: {
            title: {
              type: "string",
              title: "Titel",
              "x-rules": ["required"],
            },
            const: {
              type: "string",
              title: "Value",
              "x-rules": ["required"],
            },
          },
        },
      },
    },
  } as any);
  return schema;
};

export const multiselectSchema = (labels: Labels) => {
  const schema = schemaBuilder("array", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "array",
    title: `${labels.default}`,
    items: {
      type: "string",
    },
    "x-props": {
      outlined: true,
    },
    "x-rules": ["required"],
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 12,
      },
    },
  };
  (schema.allOf[0].properties as any)["x-display"] = {
    type: "string",
    title: "Display",
    enum: ["checkbox", "select", "switch"],
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 6,
      },
    },
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  schema.allOf.splice(1, 0, {
    title: "Auswahl",
    type: "object",
    properties: {
      items: {
        type: "object",
        properties: {
          type: {
            const: "string",
          },
          anyOf: {
            type: "array",
            title: "Einträge",
            "x-itemTitle": "title",
            items: {
              type: "object",
              properties: {
                title: {
                  type: "string",
                  title: "Titel",
                  "x-rules": ["required"],
                },
                const: {
                  type: "string",
                  title: "Wert",
                  "x-rules": ["required"],
                },
              },
            },
          },
        },
      },
    },
  } as any);
  return schema;
};

export const fileSchema = (labels: Labels) => {
  const schema = schemaBuilder("file", labels);
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "custom-multi-file-input",
  };
  (schema.allOf[0].properties as any)["filePath"] = {
    type: "string",
    title: "Filepath",
    "x-props": {
      outlined: true,
    },
    "x-rules": [],
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 12,
      },
    },
  };
  (schema.allOf[0].properties as any)["properties"] = {
    const: {key: {type: "string"}, amount: {type: "integer"}},
  };
  (schema.allOf[0].properties as any)["uuidEnabled"] = {
    type: "boolean",
    title: "Unique identifier?",
    description:
      "Creates an unique, which will add to the the directory path. It should be used in object lists.",

    default: false,
    "x-props": {
      outlined: true,
    },
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 12,
      },
    },
  };
  (schema.allOf[2].properties as any) = {
    "x-rules": {
      type: "array",
      title: "Regeln",
      items: {
        type: "string",
        enum: ["requiredObject"],
      },
      "x-display": "checkbox",
    },
    maxFiles: {
      type: "integer",
      title: "Maximum number of files",
      "x-props": {
        outlined: true,
        dense: true,
      },
    },
    maxFileSize: {
      type: "integer",
      title: "Maximum file size in MB",
      "x-props": {
        outlined: true,
        dense: true,
      },
    },
    maxTotalSize: {
      type: "integer",
      title: "Maximum total size of all files in MB",
      "x-props": {
        outlined: true,
        dense: true,
      },
    },
    accept: {
      type: "string",
      title: "Permitted file formats",
      description:
        "The file formats must be specified as MIME type and comma-separated.",
      "x-props": {
        outlined: true,
        dense: true,
      },
    },
  };
  return schema;
};

export const userinputSchema = (labels: Labels) => {
  const schema = schemaBuilder("user", labels);
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "custom-user-input",
  };
  (schema.allOf[0].properties as any)["ldap-groups"] = {
    type: "string",
    title: "Ldap Gruppen",
    "x-props": {
      outlined: true,
      dense: true,
    },
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 6,
      },
    },
  };
  (schema.allOf[0].properties as any)["default"] = {
    type: "string",
    title: `${labels.default}`,
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  return schema;
};

export const multiUserinputSchema = (labels: Labels) => {
  const schema = schemaBuilder("array", labels);
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "custom-multi-user-input",
  };
  (schema.allOf[0].properties as any)["ldap-groups"] = {
    type: "string",
    title: "Ldap Gruppen",
    "x-props": {
      outlined: true,
      dense: true,
    },
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 6,
      },
    },
  };
  (schema.allOf[0].properties as any)["items"] = {
    type: "object",
    properties: {
      type: {
        const: "string",
      },
    },
  };
  (schema.allOf[0].properties as any)["default"] = {
    type: "array",
    title: `${labels.default}`,
    items: {
      type: "string",
    },
    "x-props": {
      outlined: true,
    },
    "x-rules": ["required"],
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 12,
      },
    },
  };
  return schema;
};

export const arrayInput = (labels: Labels) => {
  const schema = schemaBuilder("array", labels);
  (schema.allOf[0].properties as any)["items"] = {
    type: "object",
    properties: {
      type: {
        type: "string",
        title: `${labels.type}`,
        enum: ["string", "integer"],
        "x-props": {
          outlined: true,
          dense: true,
        },
      },
    },
  };
  (schema.allOf[0].properties as any)["default"] = {
    type: "array",
    title: `${labels.default}`,
    items: {
      type: "string",
    },
    "x-props": {
      outlined: true,
    },
    "x-rules": ["required"],
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 12,
      },
    },
  };
  return schema;
};

export const arrayObjectInput = (labels: Labels) => {
  const schema = schemaBuilder("array", labels);
  (schema.allOf[0].properties as any)["items"] = {
    type: "object",
    properties: {
      type: {
        const: "object",
      },
      additionalProperties: {
        const: false,
      },
      properties: {
        type: "object",
      },
    },
  };
  return schema;
};

export const dmsInputSchema = (labels: Labels) => {
  // custom array type
  const schema = schemaBuilder("array", labels);
  (schema.allOf[0].properties as any)["x-display"] = {
    const: "custom-dms-input",
  };
  (schema.allOf[0].properties as any)["dmsSystem"] = {
    type: "string",
    title: `${labels.dmsSystem}`,
    default: "mucs",
    enum: ["mucs", "alw"],
    "x-props": {
      outlined: true,
    },
    "x-rules": ["required"],
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 12,
      },
    },
  };
  (schema.allOf[0].properties as any)["objectclass"] = {
    type: "string",
    title: `${labels.dmsObjectClass}`,
    default: "Schriftstueck",
    enum: [
      "Sachakte",
      "Vorgang",
      "Eingang",
      "Ausgang",
      "Intern",
      "Schriftstueck",
    ],
    "x-props": {
      outlined: true,
    },
    "x-rules": ["required"],
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 12,
      },
    },
  };
  (schema.allOf[0].properties as any)["default"] = {
    type: "array",
    title: `${labels.default}`,
    description: `${labels.dmsDefaultDescription}`,
    items: {
      type: "string",
    },
    "x-props": {
      outlined: true,
    },
    "x-options": {
      fieldColProps: {
        cols: 12,
        sm: 12,
      },
    },
  };
  (schema.allOf[0].properties as any)["items"] = {
    type: "object",
    properties: {
      type: {
        const: "string",
      },
    },
  };
  return schema;
};

export const genericSchema = (labels: Labels) => {
  const schema = schemaBuilder("generic", labels);
  (schema.allOf[0].properties as any)["default"] = {
    type: "string",
    title: `${labels.default}`,
    "x-props": {
      outlined: true,
      dense: true,
    },
  };
  return schema;
};
