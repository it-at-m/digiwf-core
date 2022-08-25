import '@mdi/font/css/materialdesignicons.css';
import Vue from 'vue'
import Vuetify from 'vuetify/lib'

Vue.use(Vuetify)

const theme = {
    themes: {
        light: {
            primary: '#b011c5',
            secondary: '#b0bec5',
            accent: '#8c9eff',
            error: '#b71c1c',
        },
    }
};

export default new Vuetify({
    theme: theme
});
