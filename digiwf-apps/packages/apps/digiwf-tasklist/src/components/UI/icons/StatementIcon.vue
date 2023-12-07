<template>
  <svg-icon
    type="mdi"
    :path="path"/>
</template>


<script>
import SvgIcon from "@jamescoyle/vue-icon";
import {mdiAlertCircle} from "@mdi/js";

export default {
  name: "StatementIcon",
  components: {
    SvgIcon
  },

  data() {
    return {
      path: mdiAlertCircle ,
    };
  }
};
</script>
