import {inject, Ref, ref} from "vue";
import {DateTime} from "luxon";

export interface Message {
  readonly time: DateTime;
  readonly message: string;
}

export interface SnackbarContext {
  readonly showMessage: (text: string) => void;
  readonly snackbarVisible: Ref<boolean>;
  readonly messageText: Ref<string>;
  readonly targetLink: Ref<string>;
  readonly messages: Ref<Message[]>;
}

export const useNotification = () => {

  const messageText = ref<string | undefined>(undefined);
  const snackbarVisible = ref<boolean>(false);
  const messages = ref<Message[]>([]);

  return {
    showMessage: (text: string) => {
      console.log("showMessage", text);
      if (text.trim().length > 0) {
        messageText.value = text;
        messages.value = [
          {
            message: text,
            time: DateTime.now()
          },
          ...messages.value
        ];
        snackbarVisible.value = true;
      }
    },
    snackbarVisible,
    messageText,
    messages
  };
};

export const SNACKBAR_CONTEXT_KEY = "snackbar";

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
export const useSnackbarContext = () => inject<SnackbarContext>(SNACKBAR_CONTEXT_KEY)!;
