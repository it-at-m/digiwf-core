const basicOptions = {
    "x-options": {
        "fieldColProps": {
            "cols": 12,
            "sm": 12
        }
    },
    "x-props": {
        "outlined": true,
        "dense": true
    }
};

export const formFieldPalette = [
    [
        "text",
        {
            "fieldType": "text",
            title: "Textfeld",
            type: "string",
            ...basicOptions
        }
    ],
    [
        "textarea",
        {
            fieldType: "textarea",
            title: "Textarea",
            "x-display": "textarea",
            type: "string",
            ...basicOptions
        }
    ],
    [
        "integer",
        {
            fieldType: "integer",
            title: "Zahl",
            type: "integer",
            ...basicOptions
        }
    ],
    [
        "number",
        {
            fieldType: "number",
            title: "Gleitkommazahl",
            type: "number",
            ...basicOptions
        }
    ],
    [
        "boolean",
        {
            fieldType: "boolean",
            title: "Checkbox",
            type: "boolean",
            "default": false,
            ...basicOptions
        }
    ],
    [
        "date",
        {
            "fieldType": "date",
            title: "Datum",
            type: "string",
            format: "date",
            ...basicOptions
        }
    ],
    [
        "time",
        {
            "fieldType": "time",
            title: "Zeit",
            type: "string",
            format: "time",
            ...basicOptions,
            "x-options": {
                "fieldColProps": {
                    "cols": 12,
                    "sm": 12
                },
                "timePickerProps": {
                    "format": "24hr"
                }
            }
        }
    ],
    [
        "select",
        {
            "fieldType": "select",
            title: "Auswahl",
            type: "string",
            anyOf: [],
            ...basicOptions
        }
    ],
    [
        "multiselect",
        {
            "fieldType": "multiselect",
            title: "Mehrfachauswahl",
            type: "array",
            anyOf: [],
            ...basicOptions,
        }
    ],
    [
        "file",
        {
            "fieldType": "file",
            title: "Dateien",
            "x-display": "custom-multi-file-input",
            type: "object",
            ...basicOptions,
            "properties": {
                "key": {
                    "type": "string"
                },
                "amount": {
                    "type": "integer"
                }
            }
        }
    ],
    [
        "benutzerauswahl",
        {
            "fieldType": "user-input",
            title: "Benutzerauswahl",
            "x-display": "custom-user-input",
            type: "string",
            ...basicOptions
        }
    ],
    [
        "multibenutzerauswahl",
        {
            "fieldType": "multi-user-input",
            title: "Mehrfache Benutzerauswahl",
            "x-display": "custom-multi-user-input",
            type: "array",
            "items": {
                "type": "string"
            },
            ...basicOptions
        }
    ],
    [
        "switch",
        {
            "fieldType": "switch",
            title: "Schalter",
            "x-display": "switch",
            "default": false,
            type: "boolean",
            ...basicOptions
        }
    ],
    [
        "array",
        {
            "fieldType": "array",
            title: "Liste",
            type: "array",
            ...basicOptions,
            "x-props": {
                "outlined": true,
                "dense": false
            },
            "items": {
                "type": "string"
            }
        },
    ],
    [
        "markdown",
        {
            "fieldType": "markdown",
            title: "Markdown",
            type: "string",
            "x-display": "markdown",
            ...basicOptions,

        },
    ]
];

export const objectPalette = [
    [
        "objectType",
        {
            "fieldType": "object",
            title: "Dynamisches Objekt",
            type: "object",
            ...basicOptions,
            "properties": {}
        },
    ],
    [
        "arrayObject",
        {
            "fieldType": "arrayObject",
            title: "Objekt-Liste",
            type: "array",
            ...basicOptions,
            "x-props": {
                "outlined": true,
                "dense": false
            },
            "items": {
                "type": "object",
                "properties": {}
            }
        },
    ]
];

export const containerPalette = [
    {
        containerType: "group",
        title: "Group",
        description: "",
        "x-options": {
            "childrenClass": "pl-0",
        },
        properties: {}
    },
    {
        containerType: "optionalContainer",
        title: "Optional Container",
        type: "object",
        description: "",
        "x-options": {
            "sectionsTitlesClasses": ["d-none"],
            "fieldColProps": {
                "cols": 12,
                "sm": 12
            }
        },
        "x-props": {
            "outlined": true,
            "dense": true
        },
        allOf: []
    }
];

export const sectionPalette = [
    {
        title: "Abschnitt",
        description: "",
        type: "object",
        "x-options": {
            "sectionsTitlesClasses": ["d-none"],
        },
        allOf: []
    }
];

export const optionalObject = [
    {
        fieldType: "optionalContainer",
        title: "Optional Fields",
        type: "object",
        description: "",
        "x-options": {
            "childrenClass": "pl-0",

            "fieldColProps": {
                "cols": 12,
                "sm": 12
            }
        },
        "x-props": {
            "outlined": true,
            "dense": true
        },
        oneOf: []
    }
];


export const optionalProperties = [
    [
        "optionalProps",
        {
            fieldType: "optionalContainer",
            title: "Optional Group",
            type: "object",
            description: "",
            "x-options": {
                "childrenClass": "pl-0",
                "fieldColProps": {
                    "cols": 12,
                    "sm": 12
                }
            },
            "x-props": {
                "outlined": true,
                "dense": true
            },
            oneOf: []
        }
    ]
];

export const optionalItem = [
    {
        fieldType: "optionalContentItem",
        title: "Optional Select Item",
        description: "",
        "x-options": {
            "childrenClass": "pl-0",
        },
        "properties": {
            "selection": {
                "fieldType": "const",
                "type": "string",
                "const": "selection1"
            }
        }
    }
];

