import { inject } from "vue";

export const DIGIWF_BASE_URL_INJECT_KEY = "digiWFBaseUrl";

export function useDigiWFBaseURL() {
  const digiWFBaseURL = inject(DIGIWF_BASE_URL_INJECT_KEY);

  return { digiWFBaseURL };
}
