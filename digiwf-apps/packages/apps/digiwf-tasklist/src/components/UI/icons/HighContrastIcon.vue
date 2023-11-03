<template>
  <svg-icon
    type="mdi"
    :path="path"/>
</template>


<script>
import SvgIcon from "@jamescoyle/vue-icon";
import {mdiContrastBox } from "@mdi/js";

export default {
  name: "HighContrastIcon",
  components: {
    SvgIcon
  },

  data() {
    return {
      path: mdiContrastBox ,
    };
  }
};
</script>
