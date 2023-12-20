import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersistence from 'vuex-persist';
import user, {UserState} from './modules/user';
import menu, {MenuState} from "../store/modules/menu";
import info, {InfoState} from "../store/modules/info";
import {filters, FilterState} from "./modules/filters";
import {accessibility} from "./modules/accessibility";

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export interface RootState {
  userState: UserState;
  menuState: MenuState;
  infoState: InfoState;
  filters: FilterState;
}

const vuexLocal = new VuexPersistence<RootState>({
  storage: window.localStorage,
  modules: ["filters", "accessibility"]
});
export const Vuexstore = new Vuex.Store<RootState>({
  modules: {
    user,
    menu,
    info,
    filters,
    accessibility
  },
  strict: debug,
  plugins: [vuexLocal.plugin]
});
export default Vuexstore;
