import { Labels } from "@/base/labels";

export function containerSchemaBuilder(fieldType: string, labels: Labels) {
  const schema =  {
    "type": "object",
    "x-display": "tabs",
    "x-props": {
      "grow": true
    },
    "x-options": {
      "childrenClass": "pl-0",
    },
    "allOf": [
      {
        "title": `${labels.general}`,
        "description": `${labels.containerDescription}`,
        "type": "object",
        "properties": getProperties(fieldType, labels)
      },
      {
        "title": `${labels.options}`,
        "description": `${labels.containerDescription}`,
        "type": "object",
        "properties": getOptionsProperties(fieldType, labels)
      }
    ]
  }

  if (fieldType !== "container") {
    (schema.allOf[0] as any)["x-options"] = {
      "removeAdditionalProperties": false
    }
  }

  return schema;
}

function getProperties(fieldType: string, labels: Labels) {
  const base = {
    "title": {
      "type": "string",
      "title": labels.title,
      "x-props": {
        "outlined": true,
        "dense": true
      },
      "x-rules": [
        "required"
      ]
    },
    "description": {
      "type": "string",
      "title": labels.description,
      "x-props": {
        "outlined": true,
        "dense": true
      }
    },
    "containerType": {
      "type": "string",
      "x-display": "hidden",
      "title": "Type",
      "readOnly": true
    },
  }

  switch (fieldType) {
    case "container":
      return {
        ...base,
        "key": {
          "type": "string",
          "title": "Key",
          "x-display": "hidden",
          "readOnly": true
        }
      }
    case "conditionalObjectContainer": {
      return {
        ...base,
        "type": {
          "const": "object"
        },
        "key": {
          "type": "string",
          "title": labels.key,
          "x-props": {
            "outlined": true,
            "dense": true
          },
          "x-rules": [
            "required"
          ]
        },
        "fieldType": {
          "type": "string",
          "x-display": "hidden",
          "title": "Type",
          "readOnly": true
        },
        "readOnly": {
          "type": "boolean",
          "title": "Readonly",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    }
    case "conditionalContainer": {
      return {
        ...base,
        "type": {
          "const": "object"
        },
        "key": {
          "type": "string",
          "x-display": "hidden",
          "title": "Key",
          "x-props": {
            "outlined": true,
            "dense": true
          },
          "x-rules": [
            "required"
          ],
          "readOnly": false
        },
        "fieldType": {
          "type": "string",
          "x-display": "hidden",
          "title": "Type",
          "readOnly": true
        },
        "readOnly": {
          "type": "boolean",
          "title": "Readonly",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    }
  }
}

function getOptionsProperties(fieldType: string, labels: Labels) {
  const base = {
    "x-props": {
      "type": "object",
      "description": labels.ui,
      "properties": {
        "dense": {
          "default": fieldType !== "container",
          "type": "boolean",
          "title": "Dense",
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
        },
        "outlined": {
          "default": fieldType !== "container",
          "type": "boolean",
          "title": "Outlined",
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
        }
      }
    }
  }
  switch (fieldType) {
    case "container":
      return {
        "x-options": {
          "type": "object",
          "properties": {
            "childrenClass": {
              "type": "string",
              "title": "CSS classes for child elements",
              "x-props": {
                "outlined": true,
                "dense": true
              },
              "x-rules": [
                "required"
              ],
              "x-options": {
                "fieldColProps": {
                  "cols": 12,
                  "sm": 6
                }
              }
            }
          },
          ...base
        }
      }
    // optional container and conditional container
    default: {
      return {
        "x-options": {
          "type": "object",
          "properties": {
            "sectionsTitlesClasses": {
              "type": "array",
              "title": "CSS classes for Section titels",
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
                  "sm": 6
                }
              }
            }
          }
        },
        ...base
      }
    }
  }

}
