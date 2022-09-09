import {containerIconMap, defaultContainerIcon, defaultIcon, iconMap} from "./IconMap";
import {conditionalContainerSchema, conditionalObjectContainerSchema, containerSchema} from "./Container";
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
import {genericSchema, schemaMap} from "./FormFields";
import {FormBuilderSettings} from "../../types";

const Settings: FormBuilderSettings = {
    iconSettings: {
        containerIconMap: containerIconMap,
        iconMap: iconMap,
        defaultIcon: defaultIcon,
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
    defaultFormFieldSchema: genericSchema,
    formFieldSchemas: schemaMap
}

export default Settings;
