<template>
  <v-list-item
      link
      class="handle"
  >
    <div class="box-view">
      <v-icon class="mr-2">
        {{ icon }}
      </v-icon>
      {{ name }}
    </div>
    <div class="drag-view">
      <div class="d-flex flex-row">
        <v-icon
            size="30"
            class="mr-5 handle"
        >
          {{ icon }}
        </v-icon>
        <div class="mt-3 d-flex align-items-start justify-content-end flex-column">
          <span class="font-weight-bold">{{ newName }}</span>
          <span>{{ newDescription }}</span>
        </div>
      </div>
    </div>
  </v-list-item>
</template>

<script lang="ts">

import {defineComponent} from "vue";

export default defineComponent({
  props: ['newName', 'name', 'newDescription', 'icon', 'dragClasses'],


})

</script>

<style scoped>
.drag-view {
  display: none;
}
</style>

