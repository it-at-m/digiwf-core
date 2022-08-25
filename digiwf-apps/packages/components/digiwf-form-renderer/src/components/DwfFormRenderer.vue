<template>
  <div>
    <v-form ref="form">
      <Jsf @input="input" v-model="currentValue" :schema="currentSchema" :options="currentOptions">
        <template v-for="(index, name) in $scopedSlots" v-slot:[name]="data">
          <slot :name="name" v-bind="data"></slot>
        </template>
      </Jsf>
    </v-form>
  </div>
</template>

<script lang="ts">
//@ts-ignore
import {computed, defineComponent, ref} from "@vue/composition-api";
import deepmerge from "deepmerge";
import {VBtn, VForm} from "vuetify/lib/components";

export default defineComponent({
  props: ['options', 'buttonText', 'value', 'schema'],
  emits: ['input'],
  components: {VBtn, VForm},
  setup(props, {emit}) {

    const currentValue = ref({});
    const defaultOptions = {
      "editMode": "inline",
      "disableSorting": true,
      "sectionsClass": "pl-0 col-12 pb-0 pt-0 pr-0",
      "objectContainerClass": "pl-0 pb-0 pt-0 pr-0",
      "timePickerProps": {
        "format": "24hr"
      },
      "messages": {
        "required": "Dieses Feld ist ein Pflichtfeld",
        "preview": "Vorschau",
        "mdeGuide": "Dokumentation",
        "continue": "weiter"
      },
    };
    const formats = {
      "time": function (e: any, t: any) {
        const r = new Date("".concat((new Date).toISOString().split("T")[0], "T").concat(e));
        return new Date(r.getTime() + 6e4 * r.getTimezoneOffset()).toLocaleTimeString(t)
      }
    }
    const rules = {
      required: function (v: any) {
        return (!!v && v !== '') || 'Dieses Feld ist ein Pflichfeld';
      },
      requiredObject: function (v: { amount: number }) {
        return (!!v && v.amount < 1) || 'Dieses Feld ist ein Pflichfeld';
      }
    };
    currentValue.value = props.value;

    const currentSchema = computed(() => {
      if (props.options && props.options.readOnly) {
        return {
          ...props.schema,
          readOnly: true
        }
      }
      return props.schema
    })

    const currentOptions = computed(() => {
      return {
        rules: rules,
        formats: formats,
        ...deepmerge(defaultOptions, props.options),
      }
    })

    const input = () => {
      emit('input', currentValue.value);
    };

    return {
      currentValue,
      currentSchema,
      input,
      currentOptions
    }

  }
})

</script>


<style>
.v-input--is-disabled:not(.v-input--is-readonly) a {
  pointer-events: all !important;
}

.read-only .v-card__text {
  color: black !important;
}

.read-only fieldset {
  background: rgb(245, 245, 245);
}

.v-input {
  margin-right: 10px !important;
}

.read-only .v-label {
  color: #222 !important;
  font-size: 16px !important;
}

.vjsf-property > .v-input--hide-details {
  margin-bottom: 15px !important;
}

.read-only > .v-text-field.v-text-field--enclosed {
  margin-bottom: 15px !important;
}

.read-only > .row > .v-text-field.v-text-field--enclosed {
  margin-bottom: 15px !important;
}

.vjsf-property > .row:last-child > .col > .v-card {
  margin-bottom: 20px !important;
}

.theme--light.v-text-field--filled > .v-input__control > .v-input__slot {
  background-color: white !important;
}

.theme--light.v-text-field--filled > .v-input__control > .v-input__slot:hover {
  background-color: white !important;
}
</style>