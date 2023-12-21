<template>
  <svg-icon
    type="mdi"
    :path="path"/>
</template>


<script>
import SvgIcon from "@jamescoyle/vue-icon";
import {mdiKeyboard} from "@mdi/js";

export default {
  name: "KeyboardAccessibilityIcon",
  components: {
    SvgIcon
  },

  data() {
    return {
      path: mdiKeyboard ,
    };
  }
};
</script>
