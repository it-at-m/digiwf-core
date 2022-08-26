import Vue from "vue";
import {FormBuilderSettings} from "@muenchen/digiwf-form-builder-settings";
import {Form} from "@muenchen/digiwf-form-renderer";

export class DwfFormBuilder extends Vue {
    value: Form;
    name: string;
    description: string;
    builderSettings: FormBuilderSettings;

    input(): any;
}
