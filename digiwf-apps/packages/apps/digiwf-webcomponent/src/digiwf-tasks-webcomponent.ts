import { defineCustomElement } from "vue";

import DigiWFTaskVueComponent from "@/digiwf-tasks-webcomponent.ce.vue";

// convert into custom element constructor
const DigiWFTasksWebcomponent = defineCustomElement(DigiWFTaskVueComponent);

// register
customElements.define("digiwf-tasks-webcomponent", DigiWFTasksWebcomponent);
