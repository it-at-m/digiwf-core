import { computed } from "vue";
import { HTML_HEADING_TAG_MAX_LEVEL } from "@/util/constants";
import { useInjectParameters } from "@/composables/useParameters";
import type { Ref } from "vue";

export function useHeadingTag(
  offset: Ref<number>
) {

  const { rootHeadingLevel } = useInjectParameters()

  const headingTag = computed(() => {
    const rootLevel = rootHeadingLevel?.value ?? 1;
    const offsetLevel = Math.max(offset.value, 0);
    const headingLevel = Math.min(rootLevel + offsetLevel, HTML_HEADING_TAG_MAX_LEVEL);
    return `h${headingLevel}`;
  });

  return {
    headingTag
  }
}
