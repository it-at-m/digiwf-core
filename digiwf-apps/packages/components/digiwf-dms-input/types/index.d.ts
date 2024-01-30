import Vue from "vue";

export class DwfDmInput extends Vue {
}

export interface Metadata {
    name: string;
    type: string;
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


