<template>
  <v-app>
    <v-app-bar
      app
      clipped-left
      dark
      color="secondary"
    >
      <v-app-bar-nav-icon
        aria-hidden="false"
        aria-label="Menü öffnen/schließen"
        @click.stop="drawer = !drawer"
      />

      <router-link
        style="text-decoration: none"
        to="/"
      >
        <v-toolbar-title class="font-weight-bold">
          <span class="white--text">Digi</span>
          <span :style="{ color: stage?.color }">WF</span>
        </v-toolbar-title>
      </router-link>
      <v-spacer />
      <span>{{ stage?.displayName }}</span>
      <v-spacer />
      <app-help-menu
        @openKeyBindingsDialoge="openKeyBindingsDialoge"
        @closeKeyBindingsDialoge="closeKeyBindingsDialoge"
      />

      {{ user?.fullInfo }}
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
      width="300"
    >
      <AppMenuList :number-of-process-instances="processInstancesCount" />
    </v-navigation-drawer>
    <v-main class="main">
      <v-banner
        v-if="appInfo && appInfo.maintenanceInfo1"
        class="maintenance"
        multi-line
        transition="slide-y-transition"
        color="orange darken-1"
        elevation="1"
        icon="mdi-alert-circle-outline"
        icon-color="black"
      >
        <p class="body-1 my-1">
          {{ appInfo.maintenanceInfo1 }}
        </p>
        <p class="body-2 my-1">
          {{ appInfo.maintenanceInfo2 }}
        </p>
      </v-banner>
      <v-banner
        :value="!loggedIn"
        icon="mdi-alert"
        color="error"
        single-line
        sticky
      >
        <template v-if="loginLoading"> Sie werden angemeldet... </template>
        <template v-else> Sie sind aktuell nicht (mehr) angemeldet! </template>
        <template #actions>
          <v-btn
            text
            :loading="loginLoading"
            @click="login"
          >
            Login
          </v-btn>
        </template>
      </v-banner>
      <app-key-bindings-dialog
        :value="showKeyBindingsModal"
        @close="closeKeyBindingsDialoge"
      />
      <v-container fluid>
        <v-fade-transition mode="out-in">
          <router-view />
        </v-fade-transition>
      </v-container>
      <snackbar />
    </v-main>
  </v-app>
</template>

<style scoped>
.maintenance >>> .v-banner__wrapper {
  padding: 0;
}

.maintenance >>> .v-avatar {
  margin: 8px;
}
</style>

<style>
.hrDividerMenu {
  border: 0;
  border-top: 1px solid #ddd;
  margin: -2px 20px 0 20px;
}

.hrDivider {
  border: 0;
  border-top: 1px solid #ddd;
  margin: 0 5px;
}

.main {
  background-color: white;
}

html {
  overflow: auto;
}

button {
  text-transform: none !important;
}

a {
  text-transform: none !important;
}

/* Set table style for markdown tables */
.vjsf-markdown-input table {
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 0.9em;
  font-family: sans-serif;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}

.vjsf-markdown-input thead tr {
  background-color: var(--v-secondary-base);
  color: white;
  text-align: left;
}

.vjsf-markdown-input th,
.vjsf-markdown-input td {
  padding: 12px 15px;
}

.vjsf-markdown-input tbody tr {
  border-bottom: 1px solid white;
}

.vjsf-markdown-input tbody tr:nth-of-type(even) {
  background-color: lightgray;
}
</style>

<script lang="ts">
import {InfoTO} from "@muenchen/digiwf-engine-api-internal";
import {defineComponent, provide, ref, watch} from "vue";

import StageInfoService, {StageInfo} from "./api/StageInfoService";
import AppMenuList from "./components/UI/appMenu/AppMenuList.vue";
import AppHelpMenu from "./components/UI/help/AppHelpMenu.vue";
import AppKeyBindingsDialog from "./components/UI/help/AppKeyBindingsDialog.vue";
import {useStore} from "./hooks/store";
import {useGetProcessInstances} from "./middleware/processInstances/processInstancesMiddleware";
import {queryClient} from "./middleware/queryClient";
import {useCurrentUserInfo} from "./middleware/user/userMiddleware";
import {apiGatewayUrl} from "./utils/envVariables";
import {NOTIFICATION_CONTEXT_KEY, useNotification} from "./middleware/snackbar";
import Snackbar from "./components/common/Snackbar.vue";

export default defineComponent({
  components: {Snackbar, AppHelpMenu, AppMenuList, AppKeyBindingsDialog },
  setup: () => {
    const drawer = ref(true);
    const processInstancesCount = ref<number | null>(null);
    const appInfo = ref<InfoTO | null>(null);
    const loggedIn = ref(true);
    const showKeyBindingsModal = ref(false);
    const stage = ref<StageInfo>(StageInfoService.getDefaultStageInfo());

    const store = useStore();
    const snackbarContext = useNotification();
    provide(NOTIFICATION_CONTEXT_KEY, snackbarContext);

    const { data: processInstances } = useGetProcessInstances(
      ref(0),
      ref(10),
      ref(undefined)
    );
    const {
      data: user,
      isLoading: loginLoading,
      refetch: refetchUser,
    } = useCurrentUserInfo();

    provide("user", user.value);

    const loadData = () => {
      StageInfoService.getStageInfo().then((stageInfo) => {
        stage.value = stageInfo;
      });
      store.dispatch("info/getInfo", false);
      drawer.value = store.getters["menu/open"];
    };

    watch(processInstances, () => {
      processInstancesCount.value = processInstances.value
        ? processInstances.value.totalElements
        : 0;
    });

    watch(
      () => store.state.menu.open,
      (menuOpen) => {
        drawer.value = menuOpen as boolean;
      }
    );

    watch(
      () => store.state.info.info,
      (info: InfoTO) => {
        appInfo.value = info;
      }
    );

    const login = (): void => {
      const popup = window.open(`${apiGatewayUrl}/loginsuccess.html`);

      popup?.focus();
      const timer = setInterval(() => {
        if (popup?.closed ?? true) {
          clearInterval(timer);
          refetchUser();
          queryClient.refetchQueries();
        }
      }, 1000);
    };

    const openKeyBindingsDialoge = (): void => {
      showKeyBindingsModal.value = true;
    };

    const closeKeyBindingsDialoge = (): void => {
      showKeyBindingsModal.value = false;
    };

    loadData();

    return {
      drawer,
      appInfo,
      user,
      openKeyBindingsDialoge,
      closeKeyBindingsDialoge,
      login,
      loggedIn,
      showKeyBindingsModal,
      loginLoading,
      processInstancesCount,
      stage,
    };
  },
});
</script>
