import Vue from "vue";
import {FormBuilderSettings} from "@dh-nx-test/digiwf-form-builder-settings";
import {Form} from "@dh-nx-test/digiwf-form-renderer";

export class DwfFormBuilder extends Vue {
    value: Form;
    name: string;
    description: string;
    builderSettings: FormBuilderSettings;

    input(): any;
}
