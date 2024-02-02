export interface Labels {
  sectionTitle: string;
  sectionDescription: string;
  optionsTitle: string;
  maxColSize: string;
  defaultColSize: string;
  colSizeSmallDevices: string;
  errorMsgPattern: string;
  errorMsgMinString: string;
  errorMsgMaxString: string;
  errorMsgMinNumber: string;
  errorMsgMaxNumber: string;
  errorMsgMinArray: string;
  errorMsgMaxArray: string;
  errorMsgMinTime: string;
  errorMsgMaxTime: string;
  validationMin: string;
  validationMax: string;
  validationAdditionalRules: string;
}


export function schemaBuilder(fieldType: string, labels: Labels) {
  return {
    "type": "object",
    "x-display": "tabs",
    "x-props": {
      "grow": true
    },
    "x-options": {
      "childrenClass": "pr-5 pl-0",
    },
    allOf: [
      {
        "title": `${labels.sectionTitle}`,
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
          "title": {
            "type": "string",
            "title": `${labels.sectionTitle}`,
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
            "title": `${labels.sectionDescription}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
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
      },
      {
        "title": `${labels.optionsTitle}`,
        "type": "object",
        "properties": {
          "x-props": {
            "type": "object",
            "description": "Ui",
            "properties": {
              "dense": {
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
          },
          "x-options": {
            "type": "object",
            "properties": {
              "fieldColProps": {
                "description": `${labels.maxColSize}`,
                "type": "object",
                "properties": {
                  "sm": {
                    "type": "integer",
                    "title": `${labels.defaultColSize}`,
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
                  "cols": {
                    "type": "integer",
                    "title": `${labels.colSizeSmallDevices}`,
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
                }
              },
              "messages": getMessageOptions(fieldType, labels)
            }
          }
        }
      },
      {
        ...getValidationOptions(fieldType, labels)
      }
    ]
  };
}

function getMessageOptions(fieldType: string, labels: Labels) {
  switch (fieldType) {
    case "string":
      return {
        "type": "object",
        "description": "Messages",
        "properties": {
          "pattern": {
            "type": "string",
            "title": `${labels.errorMsgPattern}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          },
          "minLength": {
            "type": "string",
            "title": `${labels.errorMsgMinString}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          },
          "maxLength": {
            "type": "string",
            "title": `${labels.errorMsgMaxString}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          }
        }
      };
    case "number":
      return {
        "type": "object",
        "description": "Messages",
        "properties": {
          "pattern": {
            "type": "string",
            "title": `${labels.errorMsgPattern}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          },
          "minimum": {
            "type": "string",
            "title": `${labels.errorMsgMinNumber}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          },
          "maximum": {
            "type": "string",
            "title": `${labels.errorMsgMaxNumber}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          }
        }
      };
    case "array":
      return {
        "type": "object",
        "description": "Messages",
        "properties": {
          "pattern": {
            "type": "string",
            "title": `${labels.errorMsgPattern}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          },
          "minItems": {
            "type": "string",
            "title": `${labels.errorMsgMinArray}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          },
          "maxItems": {
            "type": "string",
            "title": `${labels.errorMsgMaxArray}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          }
        }
      };
    case "date":
      return {}
    case "time":
      return {
        "type": "object",
        "description": "Messages",
        "properties": {
          "pattern": {
            "type": "string",
            "title": `${labels.errorMsgPattern}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          },
          "minimum": {
            "type": "string",
            "title": `${labels.errorMsgMinTime}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          },
          "maximum": {
            "type": "string",
            "title": `${labels.errorMsgMaxTime}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          }
        }
      };
    default:
      return {
        "type": "object",
        "description": "Messages",
        "properties": {
          "pattern": {
            "type": "string",
            "title": `${labels.errorMsgPattern}`,
            "x-props": {
              "outlined": true,
              "dense": true
            }
          }
        }
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
      "minLength": {
        "type": "integer",
        "title": `${labels.validationMin}`,
        "x-props": {
          "outlined": true,
          "dense": true
        }
      },
      "maxLength": {
        "type": "integer",
        "title": `${labels.validationMax}`,
        "x-props": {
          "outlined": true,
          "dense": true
        }
      }
    }
  }
  else if (fieldType === "number") {
    additionalValidationOptions = {
      "minimum": {
        "type": "number",
        "title": `${labels.validationMin}`,
        "x-props": {
          "outlined": true,
          "dense": true
        }
      },
      "maximum": {
        "type": "number",
        "title": `${labels.validationMax}`,
        "x-props": {
          "outlined": true,
          "dense": true
        }
      }
    }
  }
  else if (fieldType === "array") {
    additionalValidationOptions = {
      "minItems": {
        "type": "integer",
        "title": `${labels.validationMin}`,
        "x-props": {
          "outlined": true,
          "dense": true
        }
      },
      "maxItems": {
        "type": "integer",
        "title": `${labels.validationMax}`,
        "x-props": {
          "outlined": true,
          "dense": true
        }
      }
    }
  }

  return {
    "title": "Validierung",
    "type": "object",
    "properties": {
      "pattern": {
        "type": "string",
        "title": "Pattern (regex)",
        "x-props": {
          "outlined": true,
          "dense": true
        }
      },
      "x-rules": {
        "type": "array",
        "title": `${labels.validationAdditionalRules}`,
        "items": {
          "type": "string",
          "enum": [
            "required",
          ]
        },
        "x-display": "checkbox"
      },
      ...additionalValidationOptions
    }
  };
}
