<template>
  <c-card>
    <c-card-header>
      <div class="d-flex justify-content-between align-items-center">
        <div class="d-flex align-items-center">
          <c-spinner
            v-if="loading"
            color="primary"
            class="me-3"
          />
          <svg-icon
            v-else
            type="mdi"
            :path="iconPath"
            class="me-3"
            size="36"
          />
          <h5
            v-if="loading"
            class="mb-0"
          >
            <strong>{{ cardTitle }} werden geladen...</strong>
          </h5>
          <h5
            v-else
            class="mb-0 p-0"
          >
            <strong>{{ cardTitle }}</strong>
          </h5>
        </div>
        <c-button
          type="submit"
          :disabled="loading"
          @click="emit('reload')"
        >
          <span class="me-2">Aktualisieren</span>
          <svg-icon
            type="mdi"
            :path="mdiReload"
          />
        </c-button>
      </div>
    </c-card-header>
    <c-card-body class="p-0">
      <slot
        v-if="loading"
        name="placeholder"
      />
      <error-data v-else-if="error" />
      <slot
        v-else-if="hasContent"
        name="content"
      />
      <no-data v-else />
    </c-card-body>
    <c-card-footer>
      <div
        class="d-flex w-100 align-items-center"
        :class="[
          showPagination ? 'justify-content-between' : 'justify-content-end',
        ]"
      >
        <smart-pagination
          v-if="showPagination"
          :active-page="pageData!.number!"
          :amount-pages="pageData!.totalPages!"
          @changepage="(page) => emit('changepage', page)"
        />
        <c-button
          variant="ghost"
          component="a"
          :href="frontendURL"
          target="_blank"
          class="d-flex justify-content-around align-content-center"
          role="button"
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
import type { PageData } from "@/types/PageData";

import {
  CButton,
  CCard,
  CCardBody,
  CCardFooter,
  CCardHeader,
  CSpinner,
} from "@coreui/vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiClipboardTextOutline, mdiOpenInNew, mdiReload } from "@mdi/js";
import { computed } from "vue";

import ErrorData from "@/components/common/ErrorData.vue";
import NoData from "@/components/common/NoData.vue";
import SmartPagination from "@/components/common/SmartPagination.vue";
import { useInjectBaseURL } from "@/composables/useBaseURL";

const { digiWFBaseURL } = useInjectBaseURL();

const props = withDefaults(
  defineProps<{
    iconPath?: string;
    cardTitle: string;
    loading?: boolean;
    error?: boolean;
    linkText?: string;
    linkPath: string;
    pageData: PageData;
  }>(),
  {
    iconPath: mdiClipboardTextOutline,
    loading: false,
    error: false,
    linkText: "In DigiWF öffnen",
  }
);

const emit = defineEmits<{
  reload: [];
  changepage: [page: number];
}>();

const showPagination = computed(
  () => props.pageData.totalPages && props.pageData.totalPages > 1
);

const hasContent = computed(
  () => props.pageData.totalElements && props.pageData.totalElements > 0
);

const frontendURL = computed(() => {
  return `${digiWFBaseURL!.value}/#/${props.linkPath}`;
});
</script>

<style scoped>
.spinner-border {
  color: var(
    --digiwf-webcomponent-color-icon,
    var(--digiwf-webcomponent-color-icon-default)
  ) !important;
}
svg {
  color: var(
    --digiwf-webcomponent-color-icon,
    var(--digiwf-webcomponent-color-icon-default)
  );
}
.btn-undefined > svg {
  color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );

}
.card {
  --cui-card-cap-bg: var(
    --digiwf-webcomponent-color-background,
    var(--digiwf-webcomponent-color-background-default)
  );
  --cui-card-border-radius: var(
    --digiwf-webcomponent-border-radius,
    var(--digiwf-webcomponent-border-radius-default)
  );
  --cui-card-inner-border-radius: var(
    --digiwf-webcomponent-border-radius,
    var(--digiwf-webcomponent-border-radius-default)
  );
  --cui-card-box-shadow: var(
    --digiwf-webcomponent-shadow,
    var(--digiwf-webcomponent-shadow-default)
  );
}
.card-header {
  --cui-heading-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
}
.btn {
  --cui-btn-box-shadow: none;
  --cui-btn-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
  --cui-btn-border-radius: var(
    --digiwf-webcomponent-border-radius,
    var(--digiwf-webcomponent-border-radius-default)
  );
  --cui-btn-border-width: none;
}
.btn-undefined {
  --cui-btn-bg: var(
    --digiwf-webcomponent-color-primary,
    var(--digiwf-webcomponent-color-primary-default)
  );
  --cui-btn-hover-bg: var(
    --digiwf-webcomponent-color-hover,
    var(--digiwf-webcomponent-color-hover-default)
  );
  --cui-btn-active-bg: var(
    --digiwf-webcomponent-color-hover,
    var(--digiwf-webcomponent-color-hover-default)
  );
}
</style>
