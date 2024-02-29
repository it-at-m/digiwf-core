import type { PageData } from "@/types/PageData";
import type { Ref } from "vue";

import { ref, watch, computed, readonly } from "vue";

export function usePagination(totalPages: Ref<number | undefined>, totalElements: Ref<number | undefined>) {
  const pageInternal = ref(0);
  const page = readonly(pageInternal);

  const pageSizeInternal = ref(3);
  const pageSize = readonly(pageSizeInternal);

  const setPage = (newPage: number) => {
    pageInternal.value = newPage;
  }

  watch(totalPages, (newTotalPages) => {
    if (newTotalPages && pageInternal.value + 1 > newTotalPages) {
      pageInternal.value = newTotalPages - 1;
    }
  });

  const pageData = computed<PageData>(() => {
    return {
      totalPages: totalPages.value,
      number: pageInternal.value,
      totalElements: totalElements.value,
    };
  });

  return {
    page,
    pageData,
    pageSize,
    setPage
  }
}
