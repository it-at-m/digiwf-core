import { Labels } from "@/base/labels";

const basicOptions = {
  "x-options": {
    fieldColProps: {
      cols: 12,
      sm: 12,
    },
  },
  "x-props": {
    outlined: true,
    dense: true,
  },
};

export function formFieldPaletteBuilder(labels: Labels) {
  return [
    [
      "text",
      {
        ...basicOptions,
        fieldType: "text",
        title: labels.text,
        type: "string",
      },
    ],
    [
      "textarea",
      {
        ...basicOptions,
        fieldType: "textarea",
        title: labels.textarea,
        "x-display": "textarea",
        type: "string",
      },
    ],
    [
      "integer",
      {
        ...basicOptions,
        fieldType: "integer",
        title: labels.number,
        type: "integer",
      },
    ],
    [
      "number",
      {
        ...basicOptions,
        fieldType: "number",
        title: labels.floatNumber,
        type: "number",
      },
    ],
    [
      "boolean",
      {
        ...basicOptions,
        fieldType: "boolean",
        title: labels.checkbox,
        default: false,
        type: "boolean",
      },
    ],
    [
      "date",
      {
        ...basicOptions,
        fieldType: "date",
        title: labels.date,
        "x-display": "custom-date-input",
        type: "string",
        format: "date",
      },
    ],
    [
      "time",
      {
        fieldType: "time",
        title: labels.time,
        "x-display": "custom-time-input",
        type: "string",
        format: "time",
        ...basicOptions,
        "x-options": {
          fieldColProps: {
            cols: 12,
            sm: 12,
          },
          timePickerProps: {
            format: "24hr",
          },
        },
      },
    ],
    [
      "select",
      {
        ...basicOptions,
        fieldType: "select",
        title: labels.select,
        type: "string",
        anyOf: [],
      },
    ],
    [
      "multiselect",
      {
        ...basicOptions,
        fieldType: "multiselect",
        title: labels.multiSelect,
        type: "array",
        anyOf: [],
      },
    ],
    [
      "file",
      {
        ...basicOptions,
        fieldType: "file",
        title: labels.files,
        "x-display": "file",
        type: "object",
        properties: {
          key: {
            type: "string",
          },
          amount: {
            type: "integer",
          },
        },
      },
    ],
    [
      "benutzerauswahl",
      {
        ...basicOptions,
        fieldType: "user-input",
        title: labels.userInput,
        "x-display": "custom-user-input",
        type: "string",
      },
    ],
    [
      "multibenutzerauswahl",
      {
        ...basicOptions,
        fieldType: "multi-user-input",
        title: labels.multiUserInput,
        "x-display": "custom-multi-user-input",
        type: "array",
        items: {
          type: "string",
        },
      },
    ],
    [
      "dmsinput",
      {
        ...basicOptions,
        fieldType: "dms-input",
        title: labels.dmsInput,
        "x-display": "custom-dms-input",
        type: "array",
        items: {
          type: "object",
        },
      },
    ],
    [
      "switch",
      {
        ...basicOptions,
        fieldType: "switch",
        title: labels.switch,
        "x-display": "switch",
        default: false,
        type: "boolean",
      },
    ],
    [
      "array",
      {
        ...basicOptions,
        fieldType: "array",
        title: labels.list,
        type: "array",
        "x-props": {
          outlined: true,
          dense: false,
        },
        items: {
          type: "string",
        },
      },
    ],
    [
      "markdown",
      {
        ...basicOptions,
        fieldType: "markdown",
        title: labels.markdown,
        type: "string",
        "x-display": "markdown",
      },
    ],
  ];
}

export function objectPaletteBuilder(labels: Labels) {
  return [
    [
      "objectType",
      {
        fieldType: "object",
        title: labels.dynamicObject,
        type: "object",
        ...basicOptions,
        properties: {},
      },
    ],
    [
      "arrayObject",
      {
        fieldType: "arrayObject",
        title: labels.dynamicObjectList,
        type: "array",
        ...basicOptions,
        "x-props": {
          outlined: true,
          dense: false,
        },
        items: {
          type: "object",
          properties: {},
        },
      },
    ],
  ];
}

export function optionalObjectPaletteBuilder(labels: Labels) {
  return [
    {
      fieldType: "optionalContainer",
      title: labels.optionalFields,
      type: "object",
      description: labels.description,
      "x-options": {
        childrenClass: "pl-0",

        fieldColProps: {
          cols: 12,
          sm: 12,
        },
      },
      "x-props": {
        outlined: true,
        dense: true,
      },
      oneOf: [],
    },
  ];
}

export function optionalPropertiesBuilder(labels: Labels) {
  return [
    [
      "optionalProps",
      {
        fieldType: "optionalContainer",
        title: labels.optionalGroup,
        type: "object",
        description: labels.description,
        "x-options": {
          childrenClass: "pl-0",
          fieldColProps: {
            cols: 12,
            sm: 12,
          },
        },
        "x-props": {
          outlined: true,
          dense: true,
        },
        oneOf: [],
      },
    ],
  ];
}

export function optionalItemBuilder(labels: Labels) {
  return [
    {
      fieldType: "optionalContentItem",
      title: labels.optionalSelectItem,
      description: labels.description,
      "x-options": {
        childrenClass: "pl-0",
      },
      properties: {
        selection: {
          fieldType: "const",
          type: "string",
          const: "selection1",
        },
      },
    },
  ];
}

export function containerPaletteBuilder(labels: Labels) {
  return [
    {
      containerType: "group",
      title: "Group",
      description: labels.description,
      "x-options": {
        childrenClass: "pl-0",
      },
      properties: {},
    },
    {
      containerType: "optionalContainer",
      title: labels.optionalObject,
      type: "object",
      description: labels.description,
      "x-options": {
        sectionsTitlesClasses: ["d-none"],
        fieldColProps: {
          cols: 12,
          sm: 12,
        },
      },
      "x-props": {
        outlined: true,
        dense: true,
      },
      allOf: [],
    },
  ];
}

export function sectionPaletteBuilder(labels: Labels) {
  return [
    {
      title: labels.section,
      description: labels.description,
      type: "object",
      "x-options": {
        sectionsTitlesClasses: ["d-none"],
      },
      allOf: [],
    },
  ];
}
