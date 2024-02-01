<template>
  <c-card>
    <c-card-header>
      <div class="d-flex justify-content-between align-items-center">
        <div class="d-flex">
          <CSpinner color="primary" size="xl" v-if="loading" class="me-3"/>
          <svg-icon
            v-else
            type="mdi"
            :path="iconPath"
            class="me-3 text-primary"
            size="36"
          />
          <h5 v-if="loading" class="mb-0"><strong>{{ cardTitle }} werden geladen...</strong></h5>
          <h5 v-else class="mb-0 p-0"><strong>{{ cardTitle }}</strong></h5>
        </div>
        <c-button
          color="primary"
          variant="ghost"
          :disabled="loading"
        >
          <span class="me-2">Neu laden</span>
          <svg-icon
            type="mdi"
            :path="mdiReload"
          />
        </c-button>
      </div>
    </c-card-header>
    <c-card-body class="p-0">
      <slot/>
    </c-card-body>
    <c-card-footer>
      <div class="d-flex w-100 justify-content-end align-items-center">
        <c-button
          color="primary"
          variant="ghost"
          component="a"
          :href="frontendURL"
          target="_blank"
          class="d-flex justify-content-around align-content-center"
        >
          <span class="me-2">{{ linkText }}</span>
          <svg-icon
            type="mdi"
            :path="mdiOpenInNew"
          />
        </c-button>
      </div>
    </c-card-footer>
  </c-card>
</template>

<script setup lang="ts">
import {
  CButton,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CSpinner
} from "@coreui/vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiClipboardTextOutline, mdiReload, mdiOpenInNew } from "@mdi/js";
import { computed } from "vue";

import { useDigiWFBaseURL } from "@/composables/useDigiWFBaseURL";

const { digiWFBaseURL } = useDigiWFBaseURL();

const props = withDefaults(
  defineProps<{
    iconPath?: string;
    cardTitle: string;
    loading?: boolean;
    linkText?: string;
    linkPath: string;
  }>(),
  {
    iconPath: mdiClipboardTextOutline,
    loading: false,
    linkText: "In DigiWF öffnen"
  }
);

const frontendURL = computed(() => {
  return `https://${digiWFBaseURL}/#/${props.linkPath}`;
});

</script>
