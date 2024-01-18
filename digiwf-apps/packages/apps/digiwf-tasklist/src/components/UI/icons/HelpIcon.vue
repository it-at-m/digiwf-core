<template>
  <svg-icon
    type="mdi"
    :path="path"/>
</template>


<script>
import SvgIcon from "@jamescoyle/vue-icon";
import {mdiHelpCircle} from "@mdi/js";

export default {
  name: "HelpIcon",
  components: {
    SvgIcon
  },

  data() {
    return {
      path: mdiHelpCircle ,
    };
  }
};
</script>
