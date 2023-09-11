<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="600px"
  >
    <v-card>
      <v-card-title>
        <span class="text-h5">Aufgabe "{{ taskName }}" zuweisen</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-flex v-if="errorMessage">
            <AppToast
              :message="errorMessage"
              type="error"
            />
          </v-flex>
          <v-row>
            <v-col
              cols="12"
              sm="12"
            >
              Bitte wählen Sie einen Nutzer aus, welchem Sie die Aufgabe zuweisen möchten:
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="12"
              sm="12"
            >
              <base-ldap-input
                :rules="[]"
                @input="(value) => selectedUserId = value"
              />
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn
          color="blue darken-1"
          text
          @click="$emit('close')"
        >
          Abbrechen
        </v-btn>
        <v-btn
          color="blue darken-1"
          text
          :disabled="selectedUserId == undefined"
          @click="save"
        >
          Zuweisen
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";
import BaseLdapInput from "../form/BaseLdapInput.vue";
import {useAssignTaskToUserMutation} from "../../middleware/tasks/taskMiddleware";
import AppToast from "../UI/AppToast.vue";

export default defineComponent({
  components: {AppToast, BaseLdapInput},
  props: {
    taskId: {
      type: String,
      required: true
    },
    taskName: {
      type: String,
      required: true
    },
    open: {
      type: Boolean,
      required: true
    }
  },
  emits: [
    // is triggered when dialog is should be closed
    "close",
    // is triggered when assign action was successfully
    "success"
  ],
  setup: (props, ctx) => {

    const dialog = ref<boolean>(props.open);
    const selectedUserId = ref<string | undefined>();
    const assignMutation = useAssignTaskToUserMutation();
    const errorMessage = ref<string | undefined>();

    return {
      dialog,
      errorMessage,
      selectedUserId,
      save: () => {
        const userId = selectedUserId.value;
        if (!userId) {
          errorMessage.value = "Bitte wählen Sie einen Nutzer aus um diesen den Aufgabe zuzuweisen.";
          return;
        }

        assignMutation.mutateAsync({
          taskId: props.taskId,
          userId,
        })
          .then(() => {
            ctx.emit("success");
            ctx.emit("close");
          })
          .catch(() => errorMessage.value = "Aufgabe konnte nicht zugewiesen werden");
      }
    };
  }
});
</script>

<style scoped>

</style>
