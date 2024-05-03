import { inject, Ref, ref } from "vue";
import { useRouter } from "vue-router/composables";
import { RawLocation } from "vue-router/types/router";

import { useAccessibility } from "../store/modules/accessibility";


export interface NotificationContext {
  readonly showMessageAndLeavePage: (
    text: string,
    type: string,
    targetLocation: RawLocation
  ) => void;
  readonly snackbarVisible: Ref<boolean>;
  readonly messageText: Ref<string | undefined>;
  readonly forwardToTarget: () => void;
  readonly messageType: Ref<string>;
}

export enum MessageType {
  SUCCESS = "success",
  ERROR = "error",
}

export const useNotification = (): NotificationContext => {
  const { a11YNotificationEnabled } = useAccessibility();
  const messageText = ref<string | undefined>(undefined);
  const location = ref<RawLocation | undefined>();
  const snackbarVisible = ref<boolean>(false);
  const messageType = ref<MessageType>();

  const router = useRouter();

  return <NotificationContext>{
    showMessageAndLeavePage: (
      text: string,
      type: MessageType,
      targetLocation: RawLocation
    ) => {
      if (text.trim().length > 0) {
        messageText.value = text;
        location.value = targetLocation;
        messageType.value = type;

        if (!a11YNotificationEnabled()) {
          router.push(targetLocation);
          snackbarVisible.value = true;
        } else if (type === MessageType.SUCCESS) {
          router.push({ path: "/message" });
        } else {
          router.push(targetLocation);
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
    messageType,
  };
};

export const NOTIFICATION_CONTEXT_KEY = "snackbar";

// eslint-disable-next-line @typescript-eslint/no-non-null-assertion
export const useNotificationContext = () =>
  inject<NotificationContext>(NOTIFICATION_CONTEXT_KEY)!;
