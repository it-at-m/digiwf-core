import Vue from "vue";

export class DwfDmsInput extends Vue {
}

export interface Metadata {
    name: string;
    type: string | undefined;
    url: string;
}

export enum Objectclass {
  Sachakte ="Sachakte",
  Vorgang = "Vorgang",
  Eingang = "Eingang",
  Ausgang = "Ausgang",
  Intern = "Intern",
  Schriftstueck = "Schriftstueck",
}


