<template>
  <v-row>
    <v-col
      cols="12"
      sm="6"
    >
      {{action}}
    </v-col>
    <v-col
      cols="12"
      sm="6"
    >
      {{keyBinding}}
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
defineProps({
  action: {
    type:String,
    required:true
  },
  keyBinding: {
    type:String,
    required:true
  }
});
</script>

