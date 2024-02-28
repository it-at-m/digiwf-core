<template>
  <div class="mb-7">
    <v-text-field
      :id="props.schema.key"
      v-model.trim="objectInput"
      :aria-required="isRequired()"
      :disabled="requesting"
      :error="!!errorMessage"
      :label="label"
      :readonly="props.schema.readOnly"
      :rules="rules"
      hide-details
      outlined
      type="text"
      @keydown.enter.prevent="addByButton"
    >
      <template #label>
        <span>{{ label }}</span>
        <span v-if="isRequired()" aria-hidden="true" style="font-weight: bold; color: red"> *</span>
      </template>
      <template #append>
        <div
          v-if="!props.schema.readOnly"
        >
          <v-fade-transition leave-absolute>
            <v-progress-circular
              v-if="requesting"
              color="primary"
              indeterminate
            />
            <v-btn
              v-else
              class="ml-6 mb-4"
              color="primary"
              icon
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
          :errormessage="doc.errormessage"
          :metadata="doc.metadata"
          :readOnly="props.schema.readOnly"
          @remove-object="removeDocument"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts">


import {defineComponent, inject, onMounted, ref} from "vue";
import {getMetadata, Objectclass} from "@/middleware/dmsMiddleware";
import {Metadata} from "@muenchen/digiwf-dms-api-internal";
import {checkRequired} from "@/validation/required";

interface DmsObject {
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
    const objectInput = ref<string | undefined>(undefined);
    const dmsObjects = ref<DmsObject[]>([]);
    const minObjects = props.schema.minObjects;
    const maxObjects = props.schema.maxObjects;
    const minMessage =
      (!!minObjects && minObjects == 1)
        ? `Es muss mindestens ${minObjects} Objekt übergeben werden.`
        : `Es müssen mindestens ${minObjects}  Objekte übergeben werden.`;
    const maxMessage =
      (!!maxObjects && maxObjects == 1)
        ? `Es darf maximal ${minObjects} Objekt übergeben werden.`
        : `Es dürfen maximal ${maxObjects} Objekte übergeben werden.`;

    const rules = props.rules ? props.rules : [];

    rules.push(() => {
      return errorMessage.value === undefined
    })

    const mucsDmsApiEndpoint = inject<string>('mucsDmsApiEndpoint');

    const onObjectChange = () => {
      if (!props.on) {
        return;
      }
      return props.on.input(
        getMetadataOfObjects(dmsObjects.value)
      );
    };

    const getMetadataOfObjects = (objects: DmsObject[]) => {
      return objects
          .filter(doc => !!doc.metadata)
          .map(doc => doc.coo);
    }

    const validate = (number: number) => {

      if (!!minObjects && number < minObjects) {
        errorMessage.value = minMessage;
      } else if (!!maxObjects && number > maxObjects) {
        errorMessage.value = maxMessage;
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

    const addByButton = () => {
      if (!objectInput.value) {
        return;
      }
      addObject(objectInput.value);
    }

    const addObject = (coo: string) => {
      requesting.value = true;

      const input = coo.substring(coo.indexOf("COO."));

      getMetadata(objectclass, input, getApiEndpoint())
        .then(res => {
          dmsObjects.value.push({
              coo: input,
              metadata: res
            } as DmsObject
          );
        })
        .catch(() => {
          dmsObjects.value.push({
              coo: input,
              errormessage: `Das Objekt ${input} konnte nicht geladen werden.`
            } as DmsObject
          );
        })
        .finally(() => {
          requesting.value = false;
          validate(getMetadataOfObjects(dmsObjects.value).length);
          onObjectChange();
          objectInput.value = "";
        })
    }

    const isRequired = () => {
      return checkRequired(props.schema);
    }

    const removeDocument = (coo: string) => {
      const newDmsObjects = dmsObjects.value.filter(doc => doc.coo !== coo);
      dmsObjects.value = newDmsObjects
      validate(getMetadataOfObjects(newDmsObjects).length);
      onObjectChange();
      // Is necessary to trigger validation
      objectInput.value = undefined
    }

    onMounted(() => {
      validate(getMetadataOfObjects(dmsObjects.value).length);
      if (!!props.value) {
        Promise.all<DmsObject>(props.value.map((metadataOrCoo: Metadata | string) => {
          if (typeof metadataOrCoo === "string") {
            const coo = metadataOrCoo.substring(metadataOrCoo.indexOf("COO."))

            return getMetadata(objectclass, coo, getApiEndpoint())
              .then(res => {
                return {
                  coo: coo,
                  metadata: res
                } as DmsObject;

              })
              .catch(() => {
                return {
                  coo: coo,
                  errormessage: `Das Objekt ${coo} konnte nicht geladen werden.`
                } as DmsObject;
              })
          }

          return {
            coo: metadataOrCoo.url.substring(metadataOrCoo.url.indexOf("COO.")),
            metadata: metadataOrCoo
          }
        })).then(documents => {
          dmsObjects.value = documents;
          validate(getMetadataOfObjects(dmsObjects.value).length);
          onObjectChange();
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
      removeDocument,
      isRequired
    }

  }
});

</script>
