<template>
  <c-card>
    <c-card-header>
      <div class="d-flex justify-content-between align-items-center">
        <div class="d-flex align-items-center">
          <svg-icon
            v-if="loading"
            type="mdi"
            :path="mdiLoading"
            class="me-3 loader-icon"
            size="36"
            aria-label="Daten werden geladen..."
          />
          <svg-icon
            v-else
            type="mdi"
            :path="iconPath"
            class="me-3"
            size="36"
            aria-hidden="true"
          />
          <dynamic-heading class="mb-0 p-0 text-header">
            {{ cardTitle }}
          </dynamic-heading>
        </div>
        <c-button
          type="submit"
          :disabled="loading"
          title="Daten aktualisieren"
          aria-label="Daten aktualisieren"
          :aria-disabled="loading"
          @click="emit('reload')"
        >
          <svg-icon
            type="mdi"
            :path="mdiReload"
            aria-hidden="true"
          />
        </c-button>
      </div>
    </c-card-header>
    <c-card-body
      class="p-0"
      aria-live="polite"
    >
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
          :max-pages-visible="maxPagesVisible"
          @changepage="(page) => emit('changepage', page)"
        />
        <c-button
          variant="ghost"
          as="a"
          :href="frontendURL"
          target="_blank"
          class="d-flex justify-content-around align-items-center"
          :title="newTabText"
          role="link"
          :aria-label="newTabText"
        >
          <span class="me-2 text-footer">{{ linkText }}</span>
          <svg-icon
            type="mdi"
            :path="mdiOpenInNew"
            aria-hidden="true"
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
} from "@coreui/vue";
import SvgIcon from "@jamescoyle/vue-icon";
import {
  mdiClipboardTextOutline,
  mdiLoading,
  mdiOpenInNew,
  mdiReload,
} from "@mdi/js";
import { computed, toRef } from "vue";

import DynamicHeading from "@/components/common/DynamicHeading.vue";
import ErrorData from "@/components/common/ErrorData.vue";
import NoData from "@/components/common/NoData.vue";
import SmartPagination from "@/components/common/SmartPagination.vue";
import { useBaseURL } from "@/composables/useBaseURL";
import { useNewTabText } from "@/composables/useNewTabText";
import { useInjectParameters } from "@/composables/useParameters";

const { baseURL } = useBaseURL();
const { maxPagesVisible } = useInjectParameters();

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

const { newTabText } = useNewTabText(toRef(props.linkText));

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
  return `${baseURL!.value}/#/${props.linkPath}`;
});
</script>

<style scoped>
@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(359deg);
  }
}
svg {
  color: var(
    --digiwf-webcomponent-color-icon,
    var(--digiwf-webcomponent-color-icon-default)
  );
}
.loader-icon {
  animation: spin 1s ease-in-out infinite;
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
  --cui-card-border-color: var(
    --digiwf-webcomponent-color-separator,
    var(--digiwf-webcomponent-color-separator-default)
  );
  border-color: transparent;
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
.text-header {
  font: var(
    --digiwf-webcomponent-font-header,
    var(--digiwf-webcomponent-font-header-default)
  );
}
.text-footer {
  font: var(
    --digiwf-webcomponent-font-footer,
    var(--digiwf-webcomponent-font-footer-default)
  );
}
</style>
