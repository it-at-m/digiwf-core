<template>
  <v-col
    class="pa-0"
    :cols="col"
  >
    <p>
      <vue-markdown>
        {{ description }}
      </vue-markdown>
    </p>
  </v-col>
</template>

<style scoped>

</style>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {VAutocomplete} from "vuetify/lib";
import VueMarkdown from 'vue-markdown';

@Component({
  components: {
    VAutocomplete,
    VueMarkdown
  }
})
export default class BaseMarkdownOutput extends Vue {

  @Prop()
  col: string | undefined;

  @Prop()
  description: string | undefined;

}
</script>

