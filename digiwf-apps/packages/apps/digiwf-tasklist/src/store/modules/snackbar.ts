import {Levels} from "@/api/error";
import {ActionContext} from "vuex";
import {RootState} from "@/store";

export interface SnackbarState {
    message: string | undefined;
    level: Levels;
}

export default {
    namespaced: true,
    state: {
        message: undefined,
        level: Levels.INFO
    } as SnackbarState,
    getters: {},
    mutations: {
        SET_MESSAGE(state: SnackbarState, message: string): void {
            state.message = message;
        },
        SET_LEVEL(state: SnackbarState, level: Levels): void {
            state.level = level;
        }
    },
    actions: {
        showMessage(context: ActionContext<SnackbarState, RootState>, message: SnackbarState): void {
            context.commit('SET_LEVEL', message.level ? message.level : Levels.INFO);
            context.commit('SET_MESSAGE', message.message);
        }
    }
};