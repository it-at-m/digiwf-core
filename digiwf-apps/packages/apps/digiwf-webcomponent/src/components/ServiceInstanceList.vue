<template>
  <c-card>
    <c-card-header>
      <div class="d-flex justify-content-between align-items-center">
        <div class="d-flex">
          <svg-icon
              type="mdi"
              :path="mdiClipboardTextOutline"
              class="me-3"
              style="color: var(--cui-primary)"
          />
          <h5 class="mb-0"><strong>Neueste Anträge</strong></h5>
        </div>
        <c-button
          color="primary"
          variant="ghost"
        >
          <span class="me-2">Neu laden</span>
          <svg-icon
            type="mdi"
            :path="mdiReload"
          />
        </c-button>
      </div>
    </c-card-header>
    <c-card-body style="padding: 0">
      <c-list-group flush>
        <service-instance-list-item
          v-for="serviceInstance in SERVICE_INSTANCES_DUMMIES"
          :key="serviceInstance.id"
          :service-instance="serviceInstance"
        />
      </c-list-group>
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
          <span class="me-2">Alle Anträge ansehen (DigiWF)</span>
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
  CListGroup,
} from "@coreui/vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiClipboardTextOutline, mdiOpenInNew, mdiReload } from "@mdi/js";
import { computed } from "vue";

import ServiceInstanceListItem from "@/components/ServiceInstanceListItem.vue";
import { useDigiWFBaseURL } from "@/composables/useDigiWFBaseURL";
import { FRONTEND_INSTANCE_PATH } from "@/util/constants";
import SERVICE_INSTANCES_DUMMIES from "@/util/dummy_data";

const { digiWFBaseURL } = useDigiWFBaseURL();

const frontendURL = computed(() => {
  return `https://${digiWFBaseURL}/#/${FRONTEND_INSTANCE_PATH}`;
});
</script>
