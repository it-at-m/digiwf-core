import Vue, {VNode} from 'vue';
import App from './App.vue';
import vuetify from "./plugins/vuetify";
import VueCompositionAPI from '@vue/composition-api';
import './plugins/vjsf'
import './plugins/digiwf-forms'

Vue.config.productionTip = false;

Vue.use(VueCompositionAPI);

new Vue({
    vuetify,
    render: (h): VNode => h(App),
}).$mount('#app');
