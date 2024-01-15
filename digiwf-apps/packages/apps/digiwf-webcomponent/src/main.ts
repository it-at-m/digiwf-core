import { defineCustomElement } from "vue";

import DigiWFWidgetCustomElement from "@/digiwf-widget.ce.vue";

// convert into custom element constructor
const DigiWFWidget = defineCustomElement(DigiWFWidgetCustomElement);

// register
customElements.define("digiwf-widget", DigiWFWidget);
