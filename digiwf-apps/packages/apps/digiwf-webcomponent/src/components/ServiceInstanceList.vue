<template>
  <c-list-group flush>
    <service-instance-list-item
      v-for="serviceInstance in SERVICE_INSTANCES_DUMMIES"
      :key="serviceInstance.id"
      :service-instance="serviceInstance"
    />
  </c-list-group>
</template>

<script setup lang="ts">
import { CListGroup } from "@coreui/vue";

import ServiceInstanceListItem from "@/components/ServiceInstanceListItem.vue";
import SERVICE_INSTANCES_DUMMIES from "@/dev/dummy-data";
</script>
