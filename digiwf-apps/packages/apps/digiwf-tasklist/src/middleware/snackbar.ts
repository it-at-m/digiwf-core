import { DateTime } from "luxon";
import { inject, Ref, ref } from "vue";
import { useRouter } from "vue-router/composables";
import { RawLocation } from "vue-router/types/router";

import { useAccessibility } from "../store/modules/accessibility";


export interface Message {
  readonly time: DateTime;
  readonly message: string;
}

export interface NotificationContext {
  readonly showMessageAndLeavePage: (
    text: string,
    targetLocation: RawLocation
  ) => void;
  readonly snackbarVisible: Ref<boolean>;
  readonly messageText: Ref<string | undefined>;
  readonly forwardToTarget: () => void;
}

export const useNotification = (): NotificationContext => {
  const { a11YNotificationEnabled } = useAccessibility();
  const messageText = ref<string | undefined>(undefined);
  const location = ref<RawLocation | undefined>();
  const snackbarVisible = ref<boolean>(false);
  const messages = ref<Message[]>([]);

  const router = useRouter();

  return {
    showMessageAndLeavePage: (text: string, targetLocation: RawLocation) => {
      if (text.trim().length > 0) {
        messageText.value = text;
        messages.value = [
          {
            message: text,
            time: DateTime.now(),
          },
          ...messages.value,
        ];

        location.value = targetLocation;

        if (a11YNotificationEnabled()) {
          router.push({ path: "/message" });
        } else {
          router.push(targetLocation);
          snackbarVisible.value = true;
        }
      }
    },
    snackbarVisible,
    messageText,
    forwardToTarget: () => {
      const targetLocation = location.value;

      if (targetLocation) {
        router.push(targetLocation);
      }
    },
  };
};

export const NOTIFICATION_CONTEXT_KEY = "snackbar";

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
export const useNotificationContext = () =>
  inject<NotificationContext>(NOTIFICATION_CONTEXT_KEY)!;
