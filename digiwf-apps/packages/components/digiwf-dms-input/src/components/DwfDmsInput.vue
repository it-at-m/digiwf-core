<template>
  <div class="mb-7">
    <v-text-field
      :id="props.schema.key"
      v-model.trim="objectInput"
      :readonly="props.schema.readOnly"
      :rules="rules"
      outlined
      :error="!!errorMessage"
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
      <template #append-outer>
        <v-tooltip v-if="props.schema.description" :open-on-hover="false" left>
          <template v-slot:activator="{ on }">
            <v-btn icon retain-focus-on-click @blur="on.blur" @click="on.click">
              <v-icon> mdi-information</v-icon>
            </v-btn>
          </template>
          <div class="tooltip">{{ props.schema.description }}</div>
        </v-tooltip>
      </template>
    </v-text-field>
    <div
      v-if="errorMessage"
      style="color: red"
    >
      {{ errorMessage }}
    </div>
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
import {Metadata, Objectclass} from "@/types";

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
    const objectclass: Objectclass = Objectclass[props.schema.objectclass as keyof typeof Objectclass];
    const dmsSystem = props.schema.dmsSystem;
    const requesting = ref<boolean>(false);
    const errorMessage = ref<string | undefined>(undefined);
    const objectInput = ref<string>("");
    const dmsObjects = ref<DmsDocument[]>([]);
    const minObjects = props.schema.minObjects;
    const maxObjects = props.schema.maxObjects;
    const minMessage = (!!minObjects && minObjects == 1) ?
      'Es muss mindestens ' + minObjects + ' Objekt übergeben werden' :
      'Es müssen mindestens ' + minObjects + ' Objekte übergeben werden';
    const rules = props.rules ? props.rules : [];

    rules.push(() => {
      console.log("rule validator is running");
      return errorMessage.value === undefined
    })

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

    const validate = (number: number) => {
      console.log("validate: ", {number, minObjects, maxObjects})

      if (!!minObjects && number < minObjects) {
        errorMessage.value = minMessage;
      } else if (!!maxObjects && number > maxObjects) {
        errorMessage.value = 'Es dürfen maximal ' + maxObjects + ' Objekte übergeben werden';
      } else {
        errorMessage.value = undefined;
      }

    }

    const getApiEndpoint = (): string => {
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

      requesting.value = true;

      const input = coo.substring(coo.indexOf("COO."));
      console.log(input);
      // important: in this case it works
      objectInput.value = "";
      try {
        const res = await getMetadata(objectclass, input, getApiEndpoint());

        const metadata: Metadata = {
          name: res.name,
          type: res.type,
          url: res.url
        }

        dmsObjects.value.push({
          coo: input,
          metadata
        })

      } catch (error) {
        console.log("ERROR");

        dmsObjects.value.push({
          coo: input,
          errormessage: 'Das Object' + input + ' konnte nicht geladen werden.'
        })

      } finally {
        console.log("FINALLLY")
        requesting.value = false;
        validate(dmsObjects.value.length);
      }

    }

    const removeDocument = (coo: string) => {
      console.log(dmsObjects.value);
      console.log(coo);
      const newDmsObjects = dmsObjects.value.filter(doc => doc.coo !== coo);
      dmsObjects.value = newDmsObjects
      validate(newDmsObjects.length);
    }

    onMounted(() => {

      if (!!props.value) {
        console.log("Props Value ", props.value);
        dmsObjects.value = props.value.map((metadataOrCoo: Metadata) => {
          return {
            coo: metadataOrCoo.url.substring(metadataOrCoo.url.indexOf("COO.")),
            metadata: metadataOrCoo
          }
        })
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
      errorMessage,
      rules,
      addByButton,
      removeDocument
    }

  }
});

</script>

<style scoped>
</style>
