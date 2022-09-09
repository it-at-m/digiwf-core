interface IconSettings {
    iconMap: Record<string, string>;
    containerIconMap: Record<string, string>;
    defaultIcon: string;
    defaultContainerIcon: string;
}

interface ModelerPaletteSettings {
    formFields: any[];
    containers: any[];
    optionalObject: any[];
    optionalProperties: any[];
    optionalItem: any[];
    objects: any[];
    sections: any[];
}

interface FormBuilderSettings {
    iconSettings: IconSettings;
    modelerPalette: ModelerPaletteSettings;
    formFieldSchemas: Record<string, string>;
    defaultFormFieldSchema: any;
    containerSchema: any;
    sectionSchema: any;
    conditionalContainerSchema: any;
    conditionalObjectContainerSchema: any;
}

export const SettingsDE: FormBuilderSettings;
export const SettingsEN: FormBuilderSettings;
