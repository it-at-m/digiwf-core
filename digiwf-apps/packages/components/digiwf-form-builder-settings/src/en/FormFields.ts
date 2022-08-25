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
        "title": {
            "type": "string",
            "title": "Title",
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
            "title": "Description",
            "x-props": {
                "outlined": true,
                "dense": true
            }
        }
    }
};

const basicOptions = {
    "title": "Options",
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
                    "description": "Size (max. 12)",
                    "type": "object",
                    "properties":
                        {
                            "sm": {
                                "type": "integer",
                                "title": "Standard size",
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
                                "title": "Size on small devices",
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
    "title": "Validation",
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
            "title": "Further rules",
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
                    "title": "min. length",
                    "x-props": {
                        "outlined": true,
                        "dense": true
                    }
                },
                "maxLength": {
                    "type": "integer",
                    "title": "max. length",
                    "x-props": {
                        "outlined": true,
                        "dense": true
                    }
                }
            }
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
                    "title": "min. length",
                    "x-props": {
                        "outlined": true,
                        "dense": true
                    }
                },
                "maxLength": {
                    "type": "integer",
                    "title": "max. length",
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
                "format": {
                    "const": "date"
                },
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

const checkboxSchema = {
    ...basicSchema,
    allOf: [
        {
            ...basicAttributes,
            properties: {
                ...basicAttributes.properties,
                "x-display": {
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
                        "",
                        "radio"
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
                }
            }
        },
        {
            "title": "Selection",
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
                                "title": "Title",
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
                }
            }
        },
        {
            "title": "Selection",
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
                            "title": "Entries",
                            "x-itemTitle": "title",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "title": {
                                        "type": "string",
                                        "title": "Title",
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
}

const fileSchema = {
    ...basicSchema,
    allOf: [
        {
            ...basicAttributes,
            properties: {
                ...basicAttributes.properties,
                "x-display": {
                    "const": "file"
                },
                "filePath": {
                    "type": "string",
                    "title": "File path",
                    "default": "/",
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
                },
                "properties": {
                    "const": {"key": {"type": "string"}, "amount": {"type": "integer"}}
                },
                "uuidEnabled": {
                    "type": "boolean",
                    "title": "Unique identifier?",
                    "description": "Creates an unique identifier that is appended to the file path. Should be used in object lists.",
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
                    "title": "Further rules",
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
                    },

                }
            }
        },
        {
            ...basicOptions
        }
    ]
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
        }
    ]
};

export const genericSchema = {
    ...basicSchema,
    allOf: [
        {
            ...basicAttributes,
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
    "boolean": checkboxSchema,
    "select": selectSchema,
    "multiselect": multiselectSchema,
    "array": arrayInput,
    "arrayObject": arrayObjectInput,
    "objectType": objectInput,
    "object": objectInput,
    "switch": switchSchema,
    "const": constSchema,
    "file": fileSchema
};
