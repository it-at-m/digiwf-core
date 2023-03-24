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
      ...basicOptions,
      "fieldType": "text",
      title: "Textfield",
      type: "string",
    }
  ],
  [
    "textarea",
    {
      ...basicOptions,
      fieldType: "textarea",
      title: "Textarea",
      "x-display": "textarea",
      type: "string",
    }
  ],
  [
    "integer",
    {
      ...basicOptions,
      fieldType: "integer",
      title: "Number",
      type: "integer",
    }
  ],
  [
    "number",
    {
      ...basicOptions,
      fieldType: "number",
      title: "Floating Number",
      type: "number",
    }
  ],
  [
    "boolean",
    {
      ...basicOptions,
      fieldType: "boolean",
      title: "Checkbox",
      "default": false,
      type: "boolean",
    }
  ],
  [
    "date",
    {
      ...basicOptions,
      "fieldType": "date",
      title: "Date",
      "x-display": "custom-date-input",
      type: "string",
      format: "date",
    }
  ],
  [
    "time",
    {
      "fieldType": "time",
      title: "Time",
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
      ...basicOptions,
      "fieldType": "select",
      title: "Select",
      type: "string",
      anyOf: [],
    }
  ],
  [
    "multiselect",
    {
      ...basicOptions,
      "fieldType": "multiselect",
      title: "Multiselect",
      type: "array",
      anyOf: [],
    }
  ],
  [
    "file",
    {
      ...basicOptions,
      "fieldType": "file",
      title: "Files",
      "x-display": "file",
      type: "object",
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
      ...basicOptions,
      "fieldType": "user-input",
      title: "Benutzerauswahl",
      "x-display": "custom-user-input",
      type: "string"
    }
  ],
  [
    "multibenutzerauswahl",
    {
    ...basicOptions,
      "fieldType": "multi-user-input",
      title: "Mehrfache Benutzerauswahl",
      "x-display": "custom-multi-user-input",
      type: "array",
      "items": {
        "type": "string"
      }
    }
  ],
  [
    "switch",
    {
      ...basicOptions,
      "fieldType": "switch",
      title: "Switch",
      "x-display": "switch",
      "default": false,
      type: "boolean",
    }
  ],
  [
    "array",
    {
      ...basicOptions,
      "fieldType": "array",
      title: "List",
      type: "array",
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
      ...basicOptions,
      "fieldType": "markdown",
      title: "Markdown",
      type: "string",
      "x-display": "markdown",
    },
  ]
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

