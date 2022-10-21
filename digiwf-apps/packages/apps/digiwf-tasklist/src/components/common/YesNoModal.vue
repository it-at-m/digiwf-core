<template>
  <div>
    <v-dialog
      v-model="dialog"
      persistent
      :width="options.width"
      v-bind:style="{ zIndex: options.zIndex }"
    >
      <v-card>
        <v-card-title v-show="!!title">{{ title }}</v-card-title>
        <v-card-text v-if="!!message">{{ message }}</v-card-text>
        <v-card-text v-else>
          <slot></slot>
        </v-card-text>
        <v-card-actions>
            <v-spacer />
          <v-btn text @click="cancel">Nein</v-btn>
          <v-btn color="primary" @click="agree">Ja</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";

@Component
export default class YesNoModal extends Vue {

  @Prop()
  title!: string;
  @Prop()
  message: string | undefined;

  dialog = false;
  options = {
      width: 800,
      zIndex: 200
  };
  resolve: any = null;
  reject: any = null;

  open(options:any) {
    this.dialog = true;
    this.options = Object.assign(this.options, options);
    setTimeout(() => this.dialog = false, 10000);
    return new Promise((resolve, reject) => {
      this.resolve = resolve;
      this.reject = reject;
    });
  };

  agree() {
    this.resolve(true);
    this.dialog = false;
  };

  cancel() {
    this.resolve(false);
    this.dialog = false;
  };


};
</script>
