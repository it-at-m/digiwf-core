<template>
  <c-list-group-item
    component="a"
    class="p-3"
  >
    <c-placeholder component="h6" :xs="4" size="lg" class="mb-3" animation="wave"/>
    <c-placeholder component="p" :xs="8" class="mb-1" animation="wave"/>
    <c-placeholder component="p" :xs="10" class="mb-0 mt-3" animation="wave"/>
  </c-list-group-item>
</template>

<script setup lang="ts">
import {
  CPlaceholder, CListGroupItem
} from "@coreui/vue";
</script>
