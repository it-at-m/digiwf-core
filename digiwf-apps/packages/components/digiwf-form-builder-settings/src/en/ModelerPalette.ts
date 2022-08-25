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
            title: "Textfield",
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
            title: "Number",
            type: "integer",
            ...basicOptions
        }
    ],
    [
        "number",
        {
            fieldType: "number",
            title: "Floating Number",
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
            ...basicOptions
        }
    ],
    [
        "date",
        {
            "fieldType": "date",
            title: "Date",
            type: "string",
            format: "date",
            ...basicOptions
        }
    ],
    [
        "select",
        {
            "fieldType": "select",
            title: "Select",
            type: "string",
            anyOf: [],
            ...basicOptions
        }
    ],
    [
        "multiselect",
        {
            "fieldType": "multiselect",
            title: "Multiselect",
            type: "array",
            anyOf: [],
            ...basicOptions,
        }
    ],
    [
        "switch",
        {
            "fieldType": "switch",
            title: "Switch",
            "x-display": "switch",
            type: "boolean",
            ...basicOptions
        }
    ],
    [
        "array",
        {
            "fieldType": "array",
            title: "List",
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
        "file",
        {
            "fieldType": "file",
            title: "Files",
            "x-display": "file",
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

];

export const objectPalette = [
    [
        "objectType",
        {
            "fieldType": "object",
            title: "Object",
            type: "object",
            ...basicOptions,
            "properties": {}
        },
    ],
    [
        "arrayObject",
        {
            "fieldType": "arrayObject",
            title: "Object List",
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
        title: "Optional Object",
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
        title: "Section",
        description: "",
        type: "object",
        "x-options": {
            "sectionsTitlesClasses": ["d-none"],
        },
        allOf: []
    }
];

