<template>
  <div class="mb-7">
    <v-text-field
      :id="props.schema.key"
      v-model.trim="documentInput"
      :readonly="readonly"
      outlined
      :error="!!errorMessage"
      hide-details
      :disabled="requesting"
      :label="label"
      type="text"
    >
      <template #append>
        <div
          v-if="!readonly"
          class="mb-2 mt-0"
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
              class="addButtonDocInput"
              text
              size="24"
              color="primary"
              @click="addDocument"
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
      v-if="errorMessage"
      style="color: red"
    >
      {{ errorMessage }}
    </div>
    <div
      v-if="documents && documents.length > 0"
      class="listWrapper"
    >
      <div
        v-for="doc in documents"
        :key="doc.url"
      >
        <v-flex class="d-flex ma-2 ml-3 align-center">
          <v-icon class="mr-2">
            {{ calculateIcon(doc.type) }}
          </v-icon>
          <a
            target="_blank"
            :href="doc.url"
          >{{ doc.name }}</a>
          <v-spacer/>
          <v-btn
            v-if="!readonly"
            icon
            @click="removeDocument(doc.url)"
          >
            <v-icon>
              mdi-close
            </v-icon>
          </v-btn>
        </v-flex>
      </div>
    </div>
  </div>
</template>

<script lang="ts">


import {defineComponent, inject, ref, watch} from "vue";
import {getMetadata} from "@/middleware/dmsMiddleware";
import {Metadata, Objectclass } from "@/types";

export default defineComponent({
  props: [
    'readonly',
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
    let documents = ref<Metadata[]> (props.value || []);
    let requesting = ref<boolean>(false);
    let errorMessage = ref<string>("");
    let documentInput = ref<string>("");

    const mucsDmsApiEndpoint = inject<string>('mucsDmsApiEndpoint');

    watch(documents.value, () => {
      if (!props.on) {
        return;
      }
      return props.on.input(
        documents.value
      );
    });

    const getApiEndpoint = () : string => {
      if (dmsSystem === "mucs" && !!mucsDmsApiEndpoint) {
        return mucsDmsApiEndpoint;
      }
      return "";
    }

    const addDocument = async () => {
      if (!documentInput.value) {
        return;
      }

      const startTime = new Date().getTime();
      requesting.value = true;
      const input = documentInput.value.substring(documentInput.value.indexOf("COO."));

      try {
        const res = await getMetadata(objectclass, input, getApiEndpoint());

        errorMessage.value = "";
        setTimeout(() => {
          documentInput.value = "";
          const metadata : Metadata = {
            name: res.name,
            type: res.type,
            url: res.url
          }
          documents.value.push(metadata);
          requesting.value = false;
        }, Math.max(0, 1000 - (new Date().getTime() - startTime)));
      } catch (error) {
        setTimeout(() => {
          errorMessage.value = 'Das Dokument konnte nicht geladen werden.';
          requesting.value = false;
        }, Math.max(0, 1000 - (new Date().getTime() - startTime)));
      }
    }

    const removeDocument = (url: string) => {
      for (let i = 0; i < documents.value.length; i++) {
        if (documents.value[i].url == url) {
          documents.value.splice(i, 1);
          break; // #838: only remove first item
        }
      }
  }

  const calculateIcon = (type: string) => {
    if (type === "PDF-Dokument") {
      return "mdi-file-pdf";
    }
    return "mdi-file";
  }

  return {
    props,
    documentInput,
    documents,
    requesting,
    errorMessage,
    addDocument,
    removeDocument,
    calculateIcon
  }

  }
});

</script>

<style scoped>


</style>
