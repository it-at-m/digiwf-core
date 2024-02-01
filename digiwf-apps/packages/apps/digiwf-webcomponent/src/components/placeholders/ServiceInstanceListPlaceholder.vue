<template>
  <c-list-group flush>
    <service-instance-list-item-placeholder/>
    <service-instance-list-item-placeholder/>
    <service-instance-list-item-placeholder/>
    <service-instance-list-item-placeholder/>
  </c-list-group>
</template>

<script setup lang="ts">
import {
  CListGroup,
} from "@coreui/vue";
import ServiceInstanceListItemPlaceholder from "@/components/placeholders/ServiceInstanceListItemPlaceholder.vue";
</script>
