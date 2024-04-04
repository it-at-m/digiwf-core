import { computed } from "vue";
import { NEW_TAB_SUFFIX } from "@/util/constants";
import type { Ref } from "vue";

export function useNewTabText(
    text: Ref<string>,
) {
    const newTabText = computed(() => {
        return `${text.value} ${NEW_TAB_SUFFIX}`
    });
    return {
        newTabText
    }
}
