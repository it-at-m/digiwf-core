import { defineCustomElement } from "vue";

import DigiWFServiceInstancesVueComponent from "@/digiwf-service-instances-webcomponent.ce.vue";

// convert into custom element constructor
const DigiWFServiceInstancesWebComponent = defineCustomElement(
  DigiWFServiceInstancesVueComponent
);

// register
customElements.define(
  "digiwf-service-instances-webcomponent",
  DigiWFServiceInstancesWebComponent
);
