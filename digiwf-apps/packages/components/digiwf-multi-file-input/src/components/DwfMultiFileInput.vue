<template>
  <div class="pa-0">
    <v-file-input
      v-model="fileValue"
      :accept="schema['accept']"
      :aria-required="isRequired()"
      :disabled="isReadonly"
      :error-messages="errorMessage"
      :hint="hint"
      :label="label"
      :loading="isLoading"
      :rules="rules"
      multiple
      outlined
      persistent-hint
      truncate-length="50"
      type="file"
      v-bind="schema['x-props']"
      @change="changeInput"
      aria-label="Datei hochladen"
    >
      <template #label>
        <span tabindex="0">{{ label }}</span>
        <span v-if="isRequired()" aria-hidden="true" style="font-weight: bold; color: red" aria-label="Eingabe ist ein Pflichtfeld"> *</span>
      </template>
      <template #append-outer>
        <v-tooltip v-if="schema.description" :open-on-hover="false" left>
          <template v-slot:activator="{ on }">
            <v-btn icon retain-focus-on-click @blur="on.blur" @click="on.click" aria-label="Beschreibung anzeigen">
              <v-icon> mdi-information</v-icon>
            </v-btn>
          </template>
          <div class="tooltip">{{ schema.description }}</div>
        </v-tooltip>
      </template>
    </v-file-input>

    <div v-if="documents && documents.length > 0" class="listWrapper">
      <template v-for="doc in documents">
        <dwf-file-preview
          :key="doc.name"
          :document="doc"
          :readonly="isReadonly"
          @remove-document="removeDocument"
        />
      </template>
    </div>
    <div style="clear: both"></div>
  </div>
</template>

<script lang="ts">
import globalAxios from "axios";
//@ts-ignore
import {v4 as uuidv4} from 'uuid';
import {DocumentData, FormContext} from "../../types";
import {computed, defineComponent, inject, onMounted, ref, watch} from "vue";
import {
  getFilenames,
  getPresignedUrlForDelete,
  getPresignedUrlForGet,
  getPresignedUrlForPost
} from "@/middleware/presignedUrls";
import {checkRequired} from "@/validation/required";
import {getMimeType, validateFileType} from "@/validation/fileType";
import DwfFilePreview from "@/components/DwfFilePreview.vue";

/**
 * existing bug!. Prepend icon cannot be overridden for set tabindex="-1". More information https://github.com/vuetifyjs/vuetify/issues/9580
 */
export default defineComponent({
  computed: {
    DwfFilePreview() {
      return DwfFilePreview
    }
  },
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
    const fileValue = ref<File[]>([]);
    const data: any = {};
    const documents = ref<DocumentData[]>([]);
    const errorMessage = ref<string>("");
    const isLoading = ref<boolean>(false);
    const uuid = ref<string>("");
    const maxFiles = props.schema.maxFiles || 10;
    const maxFileSize = props.schema.maxFileSize || 10;
    const maxTotalSize = props.schema.maxTotalSize;
    const mbInByte = 1048576;
    const hint = !!maxTotalSize ?
      `Es dürfen maximal ${maxFiles} Dateien mit einer Gesamtgröße von ${maxTotalSize} MB hochgeladen werden` :
      `Es dürfen maximal ${maxFiles} Dateien hochgeladen werden`;
    let rules = props.rules ? props.rules : true;

    const apiEndpoint = inject<string>('apiEndpoint');
    const taskServiceApiEndpoint = inject<string>('taskServiceApiEndpoint');
    const formContext = inject<FormContext>('formContext');

    const input = (value: any): any => {
      if (!props.on) {
        return;
      }
      //return without uuid if not enabled
      if (!props.schema.uuidEnabled) {
        return props.on.input({amount: value});
      }
      return props.on.input({
        key: uuid,
        amount: value
      });
    }

    const isRequired = () => {
      return checkRequired(props.schema);
    }

    const isReadonly = computed(() => {
      return (
        props.disabled ||
        props.readonly ||
        props.schema.readOnly ||
        isLoading.value
      );
    });

    watch(documents.value, (updatedDocuments) => {
      if (updatedDocuments.length > maxFiles) {
        errorMessage.value = 'Es dürfen maximal ' + maxFiles + ' Dateien übergeben werden';
      } else if (!!maxTotalSize && validateTotalSize() > maxTotalSize) {
        errorMessage.value = 'Die Gesamtgröße aller Dateien darf ' + maxTotalSize + ' MB nicht überschreiten';
      } else {
        errorMessage.value = "";
      }
    });

    const filePath = computed(() => {
      let path = props.schema.filePath ? props.schema.filePath : '';

      //append uuid to path if enabled
      if (props.schema.uuidEnabled) {
        path = path !== '' ? path + "/" + uuid.value : uuid.value;
      }

      return path;
    })

    const loadInitialValues = async () => {
      try {
        isLoading.value = true;

        // get filenames
        const filenames = await getFilenames({
          filePath,
          apiEndpoint: apiEndpoint || "",
          formContext,
          taskServiceApiEndpoint: taskServiceApiEndpoint || ""
        });
        for (const filename of filenames) {
          await loadFile(filename);
        }
        errorMessage.value = "";
        if (documents.value.length > 0) {
          // set dummy value to satisfy "required"-rule
          fileValue.value = [];
          fileValue.value.push(new File([""], documents.value[0].name));
          input(documents.value.length);
        }
      } catch (error) {
        errorMessage.value = "Die Dateien konnten nicht geladen werden.";
      }
      isLoading.value = false;
    }

    const loadFile = async (filename: string) => {
      // get presigned url
      const presignedUrl = await getPresignedUrlForGet(filename, {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });

      // get file content
      const res = await globalAxios.get(presignedUrl, {
        responseType: "arraybuffer",
      });
      let content = arrayBufferToString(res.data);
      let size = getEncodedContentSize(content);

      // push data
      const doc = createDocumentDataInstance(
        filename,
        getMimeType(filename),
        base64OfString(content),
        size
      );
      documents.value.push(doc);
    }

    const getEncodedContentSize = (content: string): number => {
      if (isBase64Encoded(content)) { // deprecated: Files are no longer serialized in Base64 encoding
        let decoded = window.atob(content);
        return decoded.length;
      }
      return content.length;
    }

    const addDocument = async (mydata: any, file: File): Promise<void> => {
      const startTime = new Date().getTime();
      isLoading.value = true;
      try {
        isLoading.value = true;

        validateFileSize(mydata);
        validateFileName(file.name);

        const presignedUrl = await getPresignedUrlForPost(file, {
          filePath,
          apiEndpoint: apiEndpoint || "",
          formContext,
          taskServiceApiEndpoint: taskServiceApiEndpoint || ""
        });

        await globalAxios.put(presignedUrl, mydata);

        let content = arrayBufferToString(mydata);

        const doc = createDocumentDataInstance(
          file!.name,
          file!.type,
          base64OfString(content),
          mydata.byteLength
        );

        documents.value.push(doc);

        errorMessage.value = "";
        isLoading.value = false;
        input(documents.value.length);
      } catch (error: any) {
        if (
          error.response &&
          error.response.status &&
          error.response.status == 409
        ) {
          errorMessage.value = "Das Dokument existiert bereits.";
        } else if (!errorMessage.value) {
          errorMessage.value = "Das Dokument konnte nicht hochgeladen werden.";
        }
        setTimeout(() => {
          isLoading.value = false;
        }, Math.max(0, 5000 - (new Date().getTime() - startTime)));
      }
      isLoading.value = false;
    }
    /**
     * TODO move to middleware after refactoring
     * @param mydata
     */
    const validateFileSize = (mydata: ArrayBuffer) => {
      if (mydata.byteLength > maxFileSize * mbInByte) {
        errorMessage.value = "Die Datei muss kleiner als " + maxFileSize + " MB sein.";
        throw new Error("File too large.");
      }
    }

    /**
     * TODO move to middleware after refactoring
     * @param name
     */
    const validateFileName = (name: string) => {

      const error = validateFileType(name, props.schema.accept)
      if(error) {
        errorMessage.value = error;
        throw new Error(error);
      }
    }

    /**
     * TODO move to middleware after refactoring
     */
    const validateTotalSize = (): number => {
      const totalSize = documents.value.reduce((accumulator, document) => document.size + accumulator, 0)
      return totalSize / mbInByte;
    }

    const createDocumentDataInstance = (
      name: string,
      type: string,
      data: string,
      size: number
    ) => {
      const doc: DocumentData = {
        type: type,
        name: name,
        data: toDataUrl(type, data),
        size: size!,
      };
      return doc;
    }

    const toDataUrl = (type: string, data: string): string => {
      return `data:${type};base64, ${data}`;
    }

    const changeInput = () => {
      if (!fileValue.value) {
        return;
      }
      errorMessage.value = "";

      fileValue.value.forEach((file) => {
        const reader = new FileReader();
        reader.onload = (event) => {
          try {
            addDocument(event.target?.result, file);
          } catch (e: any) {
            errorMessage.value = e.message;
          }
        };
        reader.readAsArrayBuffer(file);
      });
    }

    const removeDocument = async (document: DocumentData): Promise<void> => {
      for (let i = 0; i < documents.value.length; i++) {
        if (documents.value[i].name == document.name) {
          try {
            const presignedDeleteUrl = await getPresignedUrlForDelete(
              document.name,
              {
                filePath,
                apiEndpoint: apiEndpoint || "",
                formContext,
                taskServiceApiEndpoint: taskServiceApiEndpoint || ""
              }
            );
            await globalAxios.delete(presignedDeleteUrl);
            documents.value.splice(i, 1);
            if (documents.value.length == 0) {
              // set null value to violate "required"-rule
              fileValue.value = null;
            }
            break; // only remove first item
          } catch (error) {
            errorMessage.value = "Die Datei konnte nicht gelöscht werden.";
          }
        }
      }
      input(documents.value.length);
    }

    const base64OfString = (content: string) => {
      if (isBase64Encoded(content)) { // deprecated: Files are no longer serialized in Base64 encoding
        return content;
      }
      return window.btoa(content);
    }

    const arrayBufferToString = (buffer: ArrayBuffer) => {
      let content = "";
      const bytes = new Uint8Array(buffer);
      const len = bytes.byteLength;
      for (let i = 0; i < len; i++) {
        content += String.fromCharCode(bytes[i]);
      }
      return content;
    }

    const isBase64Encoded = (content: string) => {
      const base64Regex =
        /^([0-9a-zA-Z+/]{4})*(([0-9a-zA-Z+/]{2}==)|([0-9a-zA-Z+/]{3}=))?$/;
      return base64Regex.test(content);
    }

    onMounted(() => {
      if (!formContext!.id) {
        errorMessage.value = "no contextId";
        return;
      }
      //initialize uuid if enabled
      if (props.schema.uuidEnabled) {
        if (props.value && props.value.key) {
          uuid.value = props.value.key;
        } else {
          uuid.value = uuidv4();
        }
      }
      rules.push(() => documents.value.length <= maxFiles || `Es dürfen maximal ${maxFiles} Dateien übergeben werden`);
      if (!!maxTotalSize) {
        rules.push(() => validateTotalSize() <= maxTotalSize || `Die Gesamtgröße aller Dateien darf ${maxTotalSize} MB nicht überschreiten`);
      }
      loadInitialValues();
    })

    return {
      fileValue,
      data,
      documents,
      errorMessage,
      isLoading,
      uuid,
      changeInput,
      rules,
      hint,
      isReadonly,
      removeDocument,
      isRequired
    }
  }
});
</script>

<style>
/* hide last added filename in textfield */
.v-file-input .v-file-input__text {
  display: none;
}
</style>

<style scoped>
.listWrapper {
  margin-top: -6px;
  margin-bottom: 26px;
  float: left;
  display: flex;
  flex-wrap: wrap;
}

.tooltip {
  max-width: 200px;
}

.v-input--is-disabled:not(.v-input--is-readonly) {
  pointer-events: all;
}
</style>
