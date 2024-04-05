<template>
  <component :is="headingTag">
    <slot></slot>
  </component>
</template>

<script setup lang="ts">
import { toRef } from "vue";

import { useHeadingTag } from "@/composables/useHeadingTag";

const props = withDefaults(
  defineProps<{
    rootOffset?: number;
  }>(),
  {
    rootOffset: 0,
  }
);
const rootOffset = toRef(props.rootOffset);

const { headingTag } = useHeadingTag(rootOffset);
</script>
