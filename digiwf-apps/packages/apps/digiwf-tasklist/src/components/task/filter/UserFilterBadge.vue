<template>
  <v-chip
    color="primary"
    close
    @click:close="$emit('clear')"
  >
    Bearbeiter: {{ user }}
  </v-chip>
</template>

<script lang="ts">

import {getUserInfo} from "../../../middleware/user/userMiddleware";
import {ref} from "vue";

export default {
  props: {
    userId: {
      type: String,
      required: true,
    },
  },
  emits: ["clear"],
  setup: (props: { userId: string }) => {

    const user = ref<string>("-");
    getUserInfo(props.userId)
      .then((data) => {
        user.value = `${data.firstName} ${data.surname}`;
      })
      .catch(() => {
        user.value = "-";
      });

    return {
      user: user
    };
  }
};

</script>

<style scoped>

</style>
