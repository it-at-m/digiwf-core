<template>
  <v-card dark>
    <v-card-title v-show="!!title">{{ title }}</v-card-title>
    <v-card-text v-if="!!message">{{ message }}</v-card-text>
    <v-card-text v-else>
      <slot></slot>
    </v-card-text>
    <v-card-actions>
      <v-btn @click="agree">Ja</v-btn>
      <v-btn @click="cancel">Nein</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "dlg-frame",
  props: ["title", "message"],
  data: () => ({}),
  inject: ["agree", "cancel"],
  methods: {}
};
</script>
