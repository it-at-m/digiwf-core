<template>
  <c-pagination
    :size="size"
    :align="align"
  >
    <c-pagination-item
      v-if="showLeftRightButtons"
      component="button"
      :disabled="isOnFirstPage"
      @click="gotoPreviousPage"
    >
      <svg-icon
        type="mdi"
        :path="mdiArrowLeftBold"
        :size="iconSize"
      />
    </c-pagination-item>
    <template v-if="showPageButtons">
      <c-pagination-item
        v-for="page in visiblePages"
        :key="page"
        component="button"
        :active="isActivePage(page)"
        @click="gotoPage(page - 1)"
        >{{ page }}
      </c-pagination-item>
    </template>
    <c-pagination-item
      v-if="showLeftRightButtons"
      component="button"
      :disabled="isOnLastPage"
      @click="gotoNextPage"
    >
      <svg-icon
        type="mdi"
        :path="mdiArrowRightBold"
        :size="iconSize"
      />
    </c-pagination-item>
  </c-pagination>
</template>

<script setup lang="ts">
import { CPagination, CPaginationItem } from "@coreui/vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiArrowLeftBold, mdiArrowRightBold } from "@mdi/js";
import { computed, defineEmits, defineProps, withDefaults } from "vue";

import { MAX_PAGES_VISIBLE_DEFAULT } from "@/util/constants";

const props = withDefaults(
  defineProps<{
    amountPages: number;
    activePage: number;
    showLeftRightButtons?: boolean;
    showPageButtons?: boolean;
    maxPagesVisible?: number;
    align?: AlignSetting;
    size?: "sm" | "lg";
  }>(),
  {
    align: "start",
    buttonsVisible: 5,
    maxPagesVisible: MAX_PAGES_VISIBLE_DEFAULT,
    showLeftRightButtons: true,
    showPageButtons: true,
    size: undefined,
  }
);

const emit = defineEmits<{
  changepage: [page: number];
}>();

const gotoPreviousPage = () => {
  if (!isOnFirstPage.value) gotoPage(props.activePage - 1);
};

const gotoNextPage = () => {
  if (!isOnLastPage.value) gotoPage(props.activePage + 1);
};

const gotoPage = (page: number) => {
  if (page !== props.activePage) emit("changepage", page);
};

const iconSize = computed(() => {
  switch (props.size) {
    case undefined:
      return 15;
    case "lg":
      return 19;
    case "sm":
      return 14;
    default:
      return 15;
  }
});

const visiblePages = computed(() => {
  // If there are less pages than the maximum displayed pages, show all pages
  if (props.amountPages <= props.maxPagesVisible) {
    return Array.from({ length: props.amountPages }, (_, i) => i + 1);
  }

  // Determine the range of pages to display around the current page
  const rangeStart = Math.max(
    1,
    props.activePage + 1 - Math.floor(props.maxPagesVisible / 2)
  );
  const rangeEnd = Math.min(
    props.amountPages,
    rangeStart + props.maxPagesVisible - 1
  );

  // If there are too few pages to fill the maximum displayed pages, shift the range to the left
  if (rangeEnd - rangeStart + 1 < props.maxPagesVisible) {
    const offset = props.maxPagesVisible - (rangeEnd - rangeStart + 1);
    return Array.from(
      { length: props.maxPagesVisible },
      (_, i) => i + rangeStart - Math.min(offset, rangeStart - 1)
    );
  }

  // Otherwise, return the range of pages to display
  return Array.from(
    { length: rangeEnd - rangeStart + 1 },
    (_, i) => i + rangeStart
  );
});

const isActivePage = (page: number) => page === props.activePage + 1;

const isOnFirstPage = computed(() => props.activePage === 0);
const isOnLastPage = computed(() => props.activePage === props.amountPages - 1);
</script>

<style scoped>
nav :deep(ul.pagination) {
  margin-bottom: 0;
}
svg {
  color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
}
.page-item {
  --cui-pagination-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
  --cui-pagination-focus-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
  --cui-pagination-active-color: var(
    --digiwf-webcomponent-color-text,
    var(--digiwf-webcomponent-color-text-default)
  );
  --cui-pagination-border-width: none;
  --cui-pagination-border-radius: var(
    --digiwf-webcomponent-border-radius,
    var(--digiwf-webcomponent-border-radius-default)
  );
  --cui-pagination-bg: var(
    --digiwf-webcomponent-color-primary,
    var(--digiwf-webcomponent-color-primary-default)
  );
  --cui-pagination-hover-bg: var(
    --digiwf-webcomponent-color-hover,
    var(--digiwf-webcomponent-color-hover-default)
  );
  --cui-pagination-active-bg: var(
    --digiwf-webcomponent-color-hover,
    var(--digiwf-webcomponent-color-hover-default)
  );
  --cui-pagination-focus-bg: var(
    --digiwf-webcomponent-color-hover,
    var(--digiwf-webcomponent-color-hover-default)
  );
}
</style>
