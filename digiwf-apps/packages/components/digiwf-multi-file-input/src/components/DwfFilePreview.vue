<template>
  <div>
    <v-card class="doc-card mb-2" elevation="2" max-width="350px" outlined tabindex="-1" @click="openInTab()">
      <v-card-title class="text-subtitle-1 title">
        <div class="d-flex align-start flex-row" tabindex="0">
          <v-icon :aria-label="document.name" class="mr-2" left size="30">
            {{ icon }}
          </v-icon>
          {{ document.name }}
        </div>
      </v-card-title>
      <v-card-text>
        <div class="preview">
          <v-img
            v-if="isImage"
            :alt="'Bildvorschau von ' + document.name"
            :src="document.data"
            class="preview-component"
            max-width="200px"
          >
          </v-img>

          <vue2-pdf-embed
            v-else-if="isPdf"
            :aria-label="'PDF Vorschau von ' + document.name"
            :source="document.data"
            class="preview-component"

          />

          <div v-else class="preview-text">Keine Vorschau verfügbar</div>
          <div>
            <div class="footer" tabindex="0">{{ documentSize }}</div>
            <template v-if="!readonly">
              <v-btn
                :aria-label="document.name + ' entfernen'"
                class="remove-button ma-1"
                elevation="1"
                icon
                @click.stop="removeDocument"
              >
                <v-icon> mdi-delete</v-icon>
              </v-btn>
            </template>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script lang="ts">

import {computed, defineComponent} from "vue";
import {fileIcons} from "../util";
import {formatBytes} from "@/middleware/fileSize";
import {createBlobUrl} from "@/middleware/url";

export default defineComponent({
  props: ['document', 'readonly'],
  emits: ['remove-document'],
  setup(props, {emit}) {

    const calcByteCharacters = computed(() => atob(props.document.data.substr(`data:${props.document.type};base64,`.length)));

    const icon = computed(() => fileIcons[props.document.type] ?? "mdi-file");

    const isImage = computed(() => props.document.type.toLowerCase() === "image/jpeg" || props.document.type.toLowerCase() === "image/png");
    const isPdf = computed(() => props.document.type === 'application/pdf')

    const openInTab = () => {
      const url = createBlobUrl(calcByteCharacters.value, props.document.type);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", props.document.name!);
      document.body.appendChild(link);
      link.click();
    }

    const removeDocument = () => {
      emit('remove-document', props.document)
    }

    return {
      calcByteCharacters,
      icon,
      isImage,
      isPdf,
      openInTab,
      documentSize: formatBytes(props.document.size),
      removeDocument
    }
  }

})

</script>

<style scoped>
.remove-button {
  margin: 0;
  background-color: #eeeeee;
  opacity: 70%;
  position: absolute;
  right: 0;
  bottom: 0;
}

.doc-card {
  height: 200px;
  overflow: hidden;
  margin: 0 4px;
}

.title {
  background: #eeeeee;
}

.preview {
  margin: 2px 2px 2px 2px;
  padding: 5px;
}

.v-card:hover {
  background-color: #fafafa;
}

.preview-component {
  margin-left: auto;
  margin-right: auto;
}

.preview-text {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 40px;
  color: #aaaaaa;
}

.footer {
  position: absolute;
  left: 0;
  bottom: 0;
  margin-bottom: 0;
  color: #aaaaaa;
  font-size: 13px;
  background-color: #eeeeee;
  opacity: 70%;
  border-radius: 0 4px 0 0;
}
</style>
