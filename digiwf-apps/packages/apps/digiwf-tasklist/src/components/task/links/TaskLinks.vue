<script lang="ts">
import { ExternalLink } from "@muenchen/digiwf-task-api-internal";
import { PropType } from "vue";

export default {
  props: {
    links: {
      type: Array as PropType<ExternalLink[]>,
      default: () => [],
    },
  },
};
</script>

<template>
  <v-flex>
    <v-chip
      v-for="link in links"
      :key="link.label"
      outlined
      style="margin-right: 1em"
    >
      <a
        :href="link.url"
        target="_blank"
      >
        {{ link.label || link.url }}
      </a>
    </v-chip>
  </v-flex>
</template>

<style scoped></style>
