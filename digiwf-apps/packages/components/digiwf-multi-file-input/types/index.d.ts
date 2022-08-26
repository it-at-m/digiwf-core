import Vue from "vue";

export class DwfFormRenderer extends Vue {
    options: any;
    buttonText: string;
    value: any;
    schema: any;
}

export interface DocumentData {
    type: string;
    name: string;
    data: string;
    size: number;
}

export interface FormContext {
    id: string;
    type: string;
}


