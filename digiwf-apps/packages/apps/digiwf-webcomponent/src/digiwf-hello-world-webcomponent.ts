import { defineCustomElement } from "vue";

import DigiWFHelloWorldVueComponent from "@/digiwf-hello-world-webcomponent.ce.vue";

// convert into custom element constructor
const DigiWFHelloWorldWebComponent = defineCustomElement(
  DigiWFHelloWorldVueComponent
);

// register
customElements.define(
  "digiwf-hello-world-webcomponent",
  DigiWFHelloWorldWebComponent
);
