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
  Dokument = "Dokument",
  Eingang = "Eingang",
  Ausgang = "Ausgang",
  Intern = "Intern",
  Schriftstueck = "Schriftstueck",
}


