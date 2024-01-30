<template>
  <div class="mb-7">
    <v-text-field
      v-model.trim="documentInput"
      :readonly="readonly"
      class="documentInput"
      outlined
      :error="!valid && hasFocused"
      hide-details
      :disabled="requesting"
      :label="label"
      type="text"
    >
      <template #append>
        <div
          v-if="!readonly"
          class="appendWrapper"
        >
          <v-fade-transition leave-absolute>
            <v-progress-circular
              v-if="requesting"
              size="24"
              color="white"
              indeterminate
            />
            <v-btn
              v-else
              class="addButtonDocInput"
              text
              height="56"
              color="white"
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
            class="documentLink"
            :href="doc.url"
          >{{ doc.name }}</a>
          <v-spacer/>
          <v-btn
            v-if="!readonly"
            class="removeButton"
            icon
            @click="removeDocument(doc.url)"
          >
            <v-icon>
              mdi-delete
            </v-icon>
          </v-btn>
        </v-flex>
      </div>
    </div>
    <VMessages
      v-if="!valid && hasFocused"
      class="mt-1"
      :value="errorBucket"
      color="error"
    />
  </div>
</template>

<script  lang="ts">


import {defineComponent, inject} from "vue";
import {getMetadata} from "@/middleware/dmsMiddleware";
import {Metadata, Objectclass } from "@/types";


export default defineComponent({
  props: [
    'valid',
    'readonly',
    'hasFocused',
    'value',
    'options',
    'schema',
    'fullKey',
    'dense',
    'label',
    'disabled',
    'rules',
    'on'
  ],
  setup(props) {
    const objectclass = props.schema.objectclass;
    const dmsSystem = props.schema.dmsSystem;
    let model = "";
    let documents = [] as Array<Metadata>;
    let locked = false;
    let requesting = false;
    let errorMessage = "";
    let documentInput = "";

    const mucsDmsApiEndpoint = inject<string>('mucsDmsApiEndpoint');

    const addDocument = async () => {
      if (!documentInput) {
        return;
      }

      const startTime = new Date().getTime();
      requesting = true;

      try {
        locked = true;
        const res = await getMetadata(Objectclass.Ausgang, documentInput, mucsDmsApiEndpoint || "");

        errorMessage = "";
        setTimeout(() => {
          documentInput = "";
          const metadata : Metadata = {
            name: res.name,
            type: res.type,
            url: res.url
          }
          documents.push(metadata);
          requesting = false;
        }, Math.max(0, 1000 - (new Date().getTime() - startTime)));

      } catch (error) {
        setTimeout(() => {
          errorMessage = 'Das Dokument konnte nicht geladen werden.';
          requesting = false;
        }, Math.max(0, 1000 - (new Date().getTime() - startTime)));
      }
    }

    const removeDocument = (url: string) => {
      for (let i = 0; i < documents.length; i++) {
      if (documents[i].url == url) {
        documents.splice(i, 1);
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
      model,
      documentInput,
      documents,
      locked,
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

.appendWrapper {
  background-color: #333333;
  border-top-right-radius: 4px;
  margin-right: -12px;
  height: 56px;
  width: 56px;
  margin-top: -17px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.removeButton {
  margin: 0;
}

.listWrapper {
  overflow: auto;
  z-index: 10;
  border-bottom-right-radius: 4px;
  border-bottom-left-radius: 4px;
  border: 1px solid rgba(0, 0, 0, 0.38);
  border-top: 0;
}

.documentInput {
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 0;
}

.documentLink {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

</style>
