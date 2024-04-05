<template>
  <component :is="headingTag">
    <slot></slot>
  </component>
</template>

<script setup lang="ts">
import { useHeadingTag } from "@/composables/useHeadingTag";
import { toRef } from "vue";

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
