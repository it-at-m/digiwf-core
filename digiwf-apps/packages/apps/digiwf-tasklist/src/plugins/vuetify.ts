import '@mdi/font/css/materialdesignicons.css';
import Vue, {getCurrentInstance} from 'vue';
import Vuetify from 'vuetify';
import {VuetifyThemeVariant} from "vuetify/types/services/theme";
import store from '../store';

Vue.use(Vuetify);

export const lightTheme: Partial<VuetifyThemeVariant> = {
  primary: '#ff7c02',
  secondary: '#333333',
  accent: '#7BA4D9',
  success: '#69BE28',
  error: '#FF0000',

};

export const highContrastTheme: Partial<VuetifyThemeVariant> = {
  primary: {
    base: "#000000",
    lighten5: "#FFF3E0",
    lighten4: "#FFE0B2",
    lighten3: "#FFCC80",
    lighten2: "#FFB74D",
    lighten1: "#FFA726",
    darken1: "#FB8C00",
    darken2: "#F57C00",
    darken3: "#EF6C00",
    darken4: "#E65100"
  },
  secondary: '#ff0000',
  accent: '#acff1c',
  success: '#008dff',
  error: '#d24eff',
  anchor: "#ffea4b"

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
