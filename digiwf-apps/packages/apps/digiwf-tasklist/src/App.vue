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
        style="text-decoration: none;"
        to="/"
      >
        <v-toolbar-title class="font-weight-bold">
          <span class="white--text">Digi</span>
          <span style="color: #FFCC00">WF</span>
        </v-toolbar-title>
      </router-link>
      <v-spacer/>
      <span v-if="appInfo !== null">{{ appInfo.environment }}</span>
      <v-spacer/>
      {{ username }}
      <v-btn
        text
        fab
      >
        <v-icon class="white--text">
          mdi-account-circle
        </v-icon>
      </v-btn>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
      width="300"
    >
      <v-list>
        <v-list-item :to="{ path: '/mytask' }">
          <v-list-item-content class="itemContent">
            <v-list-item-title class="navigationTitle">
              <span>Meine Aufgaben</span>
              <span class="counter grey--text text--darken-2">{{ myTaskCount }}</span>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <hr class="hrDividerMenu">
        <v-list-item :to="{ path: '/instance' }">
          <v-list-item-content class="itemContent">
            <v-list-item-title class="navigationTitle">
              <span>Aktuelle Vorgänge</span>
              <span
                class="counter grey--text text--darken-2"
              >{{ processInstancesCount }}</span>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <hr class="hrDividerMenu">
        <v-list-item :to="{ path: '/process' }">
          <v-list-item-content class="itemContent">
            <v-list-item-title>Vorgang Starten</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <hr
          class="hrDividerMenu"
          style="margin-bottom: 60px"
        >
        <p class="grey--text ml-9 mt-5 mb-0">
          Gruppenaufgaben
        </p>
        <v-list-item :to="{ path: '/opengrouptask' }">
          <v-list-item-content class="itemContent">
            <v-list-item-title class="navigationTitle">
              <span>Offen</span>
              <span
                class="counter grey--text  text--darken-2"
              >{{ openGroupTaskCount }}</span>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <hr class="hrDividerMenu">
        <v-list-item :to="{ path: '/assignedgrouptask' }">
          <v-list-item-content class="itemContent">
            <v-list-item-title class="navigationTitle">
              <span>in Bearbeitung</span>
              <span
                class="counter grey--text text--darken-2"
              >{{ assignedGroupTaskCount }}</span>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <hr class="hrDividerMenu">
      </v-list>
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
      <v-container fluid>
        <v-fade-transition mode="out-in">
          <router-view/>
        </v-fade-transition>
      </v-container>
    </v-main>
  </v-app>
</template>

<style scoped>


.itemContent {
  margin: 5px 20px;
}

.counter {
  font-size: 0.9rem;
  font-weight: bold;
}

.navigationTitle {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  flex-direction: row;
}

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
import Vue from "vue";
import {Component, Watch} from "vue-property-decorator";
import {HumanTaskTO, InfoTO, ServiceInstanceTO, UserTO,} from "@muenchen/digiwf-engine-api-internal";

@Component()
export default class App extends Vue {
  drawer = true;
  query = "";
  myTaskCount: number | null = null;
  openGroupTaskCount: number | null = null;
  assignedGroupTaskCount: number | null = null;
  processInstancesCount: number | null = null;
  username = "";
  appInfo: InfoTO | null = null;

  created(): void {
    this.loadData();
  }

  mounted(): void {
    this.query = this.$route.params.query;
  }

  loadData(refresh = false): void {
    this.$store.dispatch("tasks/getTasks", refresh);
    this.$store.dispatch("openGroupTasks/getTasks", refresh);
    this.$store.dispatch("assignedGroupTasks/getTasks", refresh);
    this.$store.dispatch("processInstances/getProcessInstances", refresh);
    this.$store.dispatch("user/getUserInfo", refresh);
    this.$store.dispatch("info/getInfo", refresh);
    this.drawer = this.$store.getters["menu/open"];
  }

  @Watch("$store.state.menu.open")
  onMenuChanged(menuOpen: boolean): void {
    this.drawer = menuOpen;
  }

  @Watch("$route.params.query")
  function(query: string): void {
    if (this.query !== query) this.query = query;
  }


  @Watch("$store.state.tasks.tasks")
  setMyTaskCount(tasks: HumanTaskTO[]): void {
    const filteredTasks = tasks.filter(
      (task: HumanTaskTO) =>
        task.followUpDate == '' ||
        new Date().getTime() > new Date(task.followUpDate!).getTime()
    );
    this.myTaskCount = filteredTasks.length;
  }

  @Watch("$store.state.user.info")
  setUserName(user: UserTO): void {
    this.username = user.forename + " " + user.surname;
  }

  @Watch("$store.state.openGroupTasks.tasks")
  setOpenGroupTaskCount(tasks: HumanTaskTO[]): void {
    this.openGroupTaskCount = tasks.length;
  }

  @Watch("$store.state.assignedGroupTasks.tasks")
  setAssignedGroupTaskCount(tasks: HumanTaskTO[]): void {
    this.assignedGroupTaskCount = tasks.length;
  }

  @Watch("$store.state.processInstances.processInstances")
  setMyProcessInstancesCount(processInstances: ServiceInstanceTO[]): void {
    this.processInstancesCount = processInstances.length;
  }

  @Watch("$store.state.info.info")
  setAppInfo(info: InfoTO): void {
    this.appInfo = info;
  }
}
</script>
