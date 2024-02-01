<template>
  <v-flex class="d-flex ma-2 ml-3 align-center">
    <div v-if="metadata">
    <v-icon class="mr-2">
      {{ calculateIcon(metadata.type) }}
    </v-icon>
    <a
      target="_blank"
      :href="metadata.url"
    >{{ metadata.name }}</a>
    </div>
    <div
      style="color: red"
      v-if="errormessage"
    >
      {{ errormessage }}
    </div>
    <v-spacer/>
    <v-btn
      v-if="!readOnly"
      icon
      @click="removeObject()"
    >
      <v-icon>
        mdi-close
      </v-icon>
    </v-btn>
  </v-flex>
</template>

<script lang="ts">

import {defineComponent} from "vue";
export default defineComponent({
  props: ['coo','metadata','errormessage','readOnly'],
  emits: ['remove-object'],
  setup(props, {emit}) {

    console.log(props);
    const calculateIcon = (type: string) => {
      if (type === "PDF-Dokument") {
        return "mdi-file-pdf";
      }
      return "mdi-file";
    }
    const removeObject = () => {
      emit('remove-object', props.coo)
    }

    return {
      calculateIcon,
      removeObject,
      props
    }
  }

})
</script>

<style scoped>

</style>
