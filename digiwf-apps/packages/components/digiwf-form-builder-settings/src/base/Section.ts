import { Labels } from "@/base/labels";

export function sectionBuilder(labels: Labels) {
  return {
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
        "title": labels.general,
        "type": "object",
        "description": labels.description,
        "properties": {
          "type": {
            "const": "object"
          },
          "key": {
            "type": "string",
            "title": labels.key,
            "x-display": "hidden",
            "readOnly": true
          },
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
          }
        }
      },
      {
        "title": "Options",
        "type": "object",
        "description": labels.description,
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
          }
        }
      }
    ]
  }
}
