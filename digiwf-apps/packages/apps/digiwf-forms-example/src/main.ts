import Vue, {VNode} from 'vue';
import App from './App.vue';
import vuetify from "./plugins/vuetify";
import './plugins/vjsf'
import './plugins/digiwf-forms'

Vue.config.productionTip = false;

new Vue({
    vuetify,
    render: (h): VNode => h(App),
}).$mount('#app');
