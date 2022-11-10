import Vue from 'vue';
import Vuex from 'vuex';
import user, {UserState} from './modules/user';
import tasks, {TasksState} from "../store/modules/tasks";
import processDefinitions, {ProcessDefinitionState} from "../store/modules/processDefinitions";
import processInstances, {ProcessInstancesState} from "../store/modules/processInstances";
import openGroupTasks, {OpenGroupTasksState} from "../store/modules/openGroupTasks";
import assignedGroupTasks, {AssignedGroupTasksState} from "../store/modules/assignedGroupTasks";
import menu, {MenuState} from "../store/modules/menu";
import info, {InfoState} from "../store/modules/info";
import filters, { FiltersState } from './modules/filters';

Vue.use(Vuex);
const debug = process.env.NODE_ENV !== 'production';

export interface RootState {
  userState: UserState;
  tasksState: TasksState;
  processDefinitionState: ProcessDefinitionState;
  processInstancesState: ProcessInstancesState;
  openGroupTasksState: OpenGroupTasksState;
  assignedGroupTasksState: AssignedGroupTasksState;
  menuState: MenuState;
  infoState: InfoState;
  filtersState: FiltersState;
}

export default new Vuex.Store<RootState>({
  modules: {
    user,
    tasks,
    processDefinitions,
    processInstances,
    openGroupTasks,
    assignedGroupTasks,
    menu,
    info,
    filters
  },
  strict: debug
});
