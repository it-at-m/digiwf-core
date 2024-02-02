import { containerIconMap, defaultContainerIcon, defaultIcon, iconMap } from "./IconMap";
import { conditionalContainerSchema, conditionalObjectContainerSchema, containerSchema } from "./Container";
import section from "./Section";
import {
  containerPalette,
  formFieldPalette,
  objectPalette,
  optionalItem,
  optionalObject,
  optionalProperties,
  sectionPalette
} from "./ModelerPalette";
import { deLabels, schemaMap } from "./FormFields";
import { FormBuilderSettings } from "../../types";
import { genericSchema } from "../base/FormFields";

const Settings: FormBuilderSettings = {
    iconSettings: {
        iconMap: iconMap,
        defaultIcon: defaultIcon,
        containerIconMap: containerIconMap,
        defaultContainerIcon: defaultContainerIcon
    },
    modelerPalette: {
        formFields: formFieldPalette,
        containers: containerPalette,
        objects: objectPalette,
        sections: sectionPalette,
        optionalObject: optionalObject,
        optionalProperties: optionalProperties,
        optionalItem: optionalItem

    },
    containerSchema: containerSchema,
    conditionalContainerSchema: conditionalContainerSchema,
    conditionalObjectContainerSchema: conditionalObjectContainerSchema,
    sectionSchema: section,
    defaultFormFieldSchema: genericSchema(deLabels),
    formFieldSchemas: schemaMap
};

export default Settings;
