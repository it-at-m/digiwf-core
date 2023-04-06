const basicSchema = {
  "type": "object",
  "x-display": "tabs",
  "x-props": {
    "grow": true
  },
  "x-options": {
    "childrenClass": "pr-5 pl-0",
  }
};

const basicAttributes = {
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
    "title": {
      "type": "string",
      "title": "Titel",
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
      "title": "Beschreibung",
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
};

const basicOptions = {
  "title": "Optionen",
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
          "description": "Größe (max. 12)",
          "type": "object",
          "properties":
            {
              "sm": {
                "type": "integer",
                "title": "Standardgröße",
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
                "title": "Größe auf kleinen Geräten",
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
              "messages": {
                "type": "object",
                "description": "Messages",
                "properties": {
                  "pattern": {
                    "type": "string",
                    "title": "Pattern (Error Message)",
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    }
                  },
                  "minimum": {
                    "type": "string",
                    "title": "Minimum (Error Message)",
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    }
                  },
                  "maximum": {
                    "type": "string",
                    "title": "Maximum (Error Message)",
                    "x-props": {
                      "outlined": true,
                      "dense": true
                    }
                  }
                }
              }
            }
        },
      }
    }

  }
};

const basicValidation = {
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
      "title": "Weitere Regeln",
      "items": {
        "type": "string",
        "enum": [
          "required",
        ]
      },
      "x-display": "checkbox"
    }
  }
};

const textFeldSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      "properties": {
        ...basicAttributes.properties,
        "default": {
          "type": "string",
          "title": "Default",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation,
      properties: {
        ...basicValidation.properties,
        "minLength": {
          "type": "integer",
          "title": "min. Länge",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        },
        "maxLength": {
          "type": "integer",
          "title": "max. Länge",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    }
  ]
};

const markdownSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "const": "markdown"
        },
        "default": {
          "type": "string",
          "title": "Default",
          "x-display": "markdown",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation
    }
  ]
};

const textAreaSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "const": "textarea"
        },
        "default": {
          "type": "string",
          "title": "Default",
          "x-display": "textarea",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation,
      properties: {
        ...basicValidation.properties,
        "minLength": {
          "type": "integer",
          "title": "min. Länge",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        },
        "maxLength": {
          "type": "integer",
          "title": "max. Länge",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    }
  ]
};

const switchSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "const": "switch"
        },
        "default": {
          "type": "boolean",
          "title": "Default",
          "default": false,
          "x-display": "switch",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation
    }
  ]
};

const dateSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "const": "custom-date-input"
        },
        "default": {
          "type": "string",
          "format": "date",
          "title": "Default",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    },
    {
      // all basic options without messages
      ...basicOptions,
      properties: {
        ...basicOptions.properties,
        "x-options": {
          ...basicOptions.properties["x-options"],
          properties: {
            ...basicOptions.properties["x-options"].properties,
            fieldColProps: {
              ...basicOptions.properties["x-options"].properties.fieldColProps,
              properties: {
                ...basicOptions.properties["x-options"].properties.fieldColProps.properties,
                messages: {}
              }
            }
          }
        }
      }
    },
    {
      ...basicValidation,
      properties: {
        "x-rules": basicValidation.properties["x-rules"],
      }
    }
  ]
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

const objectInput = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "additionalProperties": {
          "const": false,
        },
      }
    },
    {
      ...basicOptions
    }
  ]
};

const timeSchema = {
    ...basicSchema,
    allOf: [
      {
        ...basicAttributes,
        properties: {
          ...basicAttributes.properties,
          "format": {
            "const": "time"
          },
          "default": {
            "type": "string",
            "format": "time",
            "title": "Default",
            "x-props": {
              "outlined": true,
              "dense": true
            }
          }

        },
      },
      {
        ...basicOptions,
        "properties": {
          ...basicOptions.properties,
          "x-options": {
            "type": "object",
            "properties": {
              "timePickerProps": {
                "type": "object",
                "properties": {
                  "format": {
                    "const": "24hr"
                  }
                }
              },
              "fieldColProps": {
                "description": "Größe (max. 12)",
                "type": "object",
                "properties":
                  {
                    "sm": {
                      "type": "integer",
                      "title": "Standardgröße",
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
                      "title": "Größe auf kleinen Geräten",
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
                    "messages": {
                      "type": "object",
                      "description": "Messages",
                      "properties": {
                        "pattern": {
                          "type": "string",
                          "title": "Pattern (Error Message)",
                          "x-props": {
                            "outlined": true,
                            "dense": true
                          }
                        },
                        "minimum": {
                          "type": "string",
                          "title": "Minimum (Error Message)",
                          "x-props": {
                            "outlined": true,
                            "dense": true
                          }
                        },
                        "maximum": {
                          "type": "string",
                          "title": "Maximum (Error Message)",
                          "x-props": {
                            "outlined": true,
                            "dense": true
                          }
                        }
                      }
                    }
                  }
              },
            }
          }
        }
      },
      {
        ...basicValidation
      }
    ]
  }
;

const checkboxSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "type": "string",
          "title": "Anzeige",
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
        "default": {
          "type": "boolean",
          "title": "Default",
          "default": false,
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }

      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation
    }
  ]
};

const selectSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "type": "string",
          "title": "Display",
          "enum": [
            "radio",
            "select"
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
        },
        "default": {
          "type": "string",
          "title": "Default",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    },
    {
      "title": "Auswahl",
      "type": "object",
      "properties": {
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
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation
    }
  ]
};

const multiselectSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
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
        },
        "default": {
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
        }
      }
    },
    {
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
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation
    }
  ]
};

const fileSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "const": "custom-multi-file-input"
        },
        "filePath": {
          "type": "string",
          "title": "Dateipfad",
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
        },
        "properties": {
          "const": {"key": {"type": "string"}, "amount": {"type": "integer"}}
        },
        "uuidEnabled": {
          "type": "boolean",
          "title": "Eindeutiger Identifikator?",
          "description": "Erzeugt einen eindeutigen Identifikator, der an den Dateipfad angehängt wird. Sollte in Objektlisten verwendet werden.",
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
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation,
      properties: {
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
        }
      }
    }
  ]
};

const userinputSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "const": "custom-user-input"
        },
        "ldap-groups": {
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
        },
        "default": {
          "type": "string",
          "title": "Default",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation
    }
  ]
};

const multiUserinputSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "x-display": {
          "const": "custom-multi-user-input"
        },
        "ldap-groups": {
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
        },
        "items": {
          "type": "object",
          "properties": {
            "type": {
              "const": "string"
            }
          }
        },
        "default": {
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
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation,
      properties: {
        "minItems": {
          "type": "integer",
          "title": "minimum",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        },
        "maxItems": {
          "type": "integer",
          "title": "maximum",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    }
  ]
};

const arrayInput = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "items": {
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
        },
        "default": {
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
        }
      }
    },
    {
      ...basicOptions
    }
  ]
};

const arrayObjectInput = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      properties: {
        ...basicAttributes.properties,
        "items": {
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
          },
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation,
      properties: {
        "minItems": {
          "type": "integer",
          "title": "minimum",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        },
        "maxItems": {
          "type": "integer",
          "title": "maximum",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    }
  ]
};

export const genericSchema = {
  ...basicSchema,
  allOf: [
    {
      ...basicAttributes,
      "properties": {
        ...basicAttributes.properties,
        "default": {
          "type": "string",
          "title": "Default",
          "x-props": {
            "outlined": true,
            "dense": true
          }
        }
      }
    },
    {
      ...basicOptions
    },
    {
      ...basicValidation
    }
  ]
};

export const schemaMap: any = {
  "textarea": textAreaSchema,
  "text": textFeldSchema,
  "date": dateSchema,
  "time": timeSchema,
  "boolean": checkboxSchema,
  "select": selectSchema,
  "multiselect": multiselectSchema,
  "file": fileSchema,
  "user-input": userinputSchema,
  "multi-user-input": multiUserinputSchema,
  "array": arrayInput,
  "arrayObject": arrayObjectInput,
  "switch": switchSchema,
  "markdown": markdownSchema,
  "const": constSchema,
  "object": objectInput,
  "objectType": objectInput,
};
