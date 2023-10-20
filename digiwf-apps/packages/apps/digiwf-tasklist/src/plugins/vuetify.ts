import "@mdi/font/css/materialdesignicons.css";
import Vue, {getCurrentInstance} from "vue";
import Vuetify from "vuetify";
import {VuetifyThemeVariant} from "vuetify/types/services/theme";
import store from "../store";

Vue.use(Vuetify);

export const lightTheme: Partial<VuetifyThemeVariant> = {
  primary: '#ff7c02',
  secondary: '#333333',
  accent: '#7BA4D9',
  success: '#69BE28',
  error: '#FF0000',
};

export const highContrastTheme: Partial<VuetifyThemeVariant> = {
  ...lightTheme,
};

// https://medium.com/@jogarcia/vuetify-multiple-themes-c580f41ece65
const theme = {
  themes: {
    light: store.getters["accessibility/isHighContrastModeEnabled"] ? highContrastTheme : lightTheme,
  },
  options: {customProperties: true}, // enable css vars
};

const vuetify = new Vuetify({
  theme: theme
});

export const useVuetify = () => {
  const instance = getCurrentInstance();
  if (!instance) {
    throw new Error("useVuetify should be called in setup().");
  }
  return instance.proxy.$vuetify;
};

export const useTheme = () => {
  const vuetify = useVuetify();
  return {
    currentTheme: vuetify.theme.currentTheme,
    activateContrastMode: () => {
      vuetify.theme.themes.light = highContrastTheme as any;
    },
    deactivateContrastMode: () => {
      vuetify.theme.themes.light = lightTheme as any;
    },
  };
};

export default vuetify;
