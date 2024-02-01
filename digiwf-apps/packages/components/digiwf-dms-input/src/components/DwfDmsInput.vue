<template>
  <div class="mb-7">
    <v-text-field
      :id="props.schema.key"
      v-model.trim="objectInput"
      :readonly="props.schema.readOnly"
      outlined
      hide-details
      :disabled="requesting"
      :label="label"
      type="text"
    >
      <template #append>
        <div
          v-if="!props.schema.readOnly"
        >
          <v-fade-transition leave-absolute>
            <v-progress-circular
              v-if="requesting"
              size="24"
              color="primary"
              indeterminate
            />
            <v-btn
              v-else
              icon
              size="24"
              color="primary"
              @click="addByButton"
            >
              <v-icon>
                mdi-file-plus-outline
              </v-icon>
            </v-btn>
          </v-fade-transition>
        </div>
      </template>
    </v-text-field>
    <div
      v-if="dmsObjects && dmsObjects.length > 0"
    >
      <div
        v-for="doc in dmsObjects"
        :key="doc.coo"
      >
        <dwf-object-preview
          :coo="doc.coo"
          :metadata="doc.metadata"
          :errormessage="doc.errormessage"
          :readOnly="props.schema.readOnly"
          @remove-object="removeDocument"
       />
      </div>
    </div>
  </div>
</template>

<script lang="ts">


import {defineComponent, inject, onMounted, ref, watch} from "vue";
import {getMetadata} from "@/middleware/dmsMiddleware";
import {Metadata, Objectclass } from "@/types";

interface DmsDocument {
  readonly coo: string;
  readonly metadata?: Metadata;
  readonly errormessage?: string;
}
export default defineComponent({
  props: [
    'value',
    'options',
    'schema',
    'dense',
    'label',
    'disabled',
    'rules',
    'on'
  ],
  setup(props) {
    const objectclass : Objectclass = Objectclass[props.schema.objectclass as keyof typeof Objectclass];
    const dmsSystem = props.schema.dmsSystem;
    let requesting = ref<boolean>(false);
    let objectInput = ref<string>("");
    const dmsObjects = ref<DmsDocument[]>([]);

    const mucsDmsApiEndpoint = inject<string>('mucsDmsApiEndpoint');

    watch(dmsObjects.value, () => {
      if (!props.on) {
        return;
      }
      const metadata = dmsObjects.value
        .map(doc => doc.metadata)
        .filter(metadata => !!metadata);
      console.log(metadata);
      return props.on.input(
       metadata
      );
    });

    const getApiEndpoint = () : string => {
      if (dmsSystem === "mucs" && !!mucsDmsApiEndpoint) {
        return mucsDmsApiEndpoint;
      }
      return "";
    }

    const addByButton = async () => {
      if (!objectInput.value) {
        return;
      }
      addObject(objectInput.value);
    }

    const addObject = async (coo: string) => {

      const startTime = new Date().getTime();
      requesting.value = true;

      const input = coo.substring(coo.indexOf("COO."));
      console.log(input);

      try {
        const res = await getMetadata(objectclass, input, getApiEndpoint());

        setTimeout(() => {
          objectInput.value = "";
          const metadata : Metadata = {
            name: res.name,
            type: res.type,
            url: res.url
          }
          dmsObjects.value.push({
            coo: input,
            metadata
          })
          requesting.value = false;
        }, Math.max(0, 1000 - (new Date().getTime() - startTime)));
      } catch (error) {
        console.log("ERROR");
        dmsObjects.value.push({
          coo: input,
          errormessage: 'Das Dokument konnte nicht geladen werden.'
        })
        setTimeout(() => {
          requesting.value = false;
        }, Math.max(0, 1000 - (new Date().getTime() - startTime)));
      }
    }

    const removeDocument = (coo: string) => {
      console.log(dmsObjects.value);
      console.log(coo);
      dmsObjects.value = dmsObjects.value.filter(doc => doc.coo!== coo);
  }

    onMounted(() => {

      if (!!props.value) {
        console.log("Props Value " , props.value);
        dmsObjects.value = props.value.map((metadataOrCoo: Metadata) => {
          return {
          coo: metadataOrCoo.url.substring(metadataOrCoo.url.indexOf("COO.")),
          metadata: metadataOrCoo
        }} )
        return;
      }
      if (!!props.schema.default) {
        props.schema.default.map(addObject);
      }
    });

  return {
    props,
    objectInput,
    dmsObjects,
    requesting,
    addByButton,
    removeDocument
  }

  }
});

</script>

<style scoped>
</style>
