export const containerSchema = {
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
            "title": "General",
            "type": "object",
            "properties": {
                "containerType": {
                    "type": "string",
                    "title": "Type",
                    "x-display": "hidden",
                    "readOnly": true
                },
                "key": {
                    "type": "string",
                    "title": "Key",
                    "x-display": "hidden",
                    "readOnly": true
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
                    "title": "Description",
                    "x-props": {
                        "outlined": true,
                        "dense": true
                    }
                }
            }
        },
        {
            "title": "Options",
            "type": "object",
            "properties": {
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
                    }
                }
            }
        }
    ]
};

export const conditionalContainerSchema = {
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
            "title": "General",
            "type": "object",
            "properties": {
                "type": {
                    "const": "object"
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
                    ],
                    "readOnly": false
                },
                "containerType": {
                    "type": "string",
                    "x-display": "hidden",
                    "title": "Type",
                    "readOnly": true
                },
                "fieldType": {
                    "type": "string",
                    "x-display": "hidden",
                    "title": "Type",
                    "readOnly": true
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
                    "title": "Description",
                    "x-props": {
                        "outlined": true,
                        "dense": true
                    }
                }
            }
        },
        {
            "title": "Options",
            "type": "object",
            "properties": {
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
                }
            }
        }
    ]
};
