<template>
  <app-view-layout>
    <div>
      <v-flex>
        <h1>Vorgänge</h1>
      </v-flex>
      <v-flex class="d-flex justify-space-between align-center searchField">
        <v-text-field
          id="suchfeld"
          v-model="filter"
          flat
          dense
          outlined
          hide-details
          label="Vorgänge durchsuchen"
          clearable
          append-icon="mdi-magnify"
          color="black"
          style="max-width: 500px"
        />
        <div class="d-flex align-center">
          <v-btn
            aria-label="Vorgänge aktualisieren"
            text
            style="padding-left: 13px;"
            large
            color="primary"
            @click="loadProcesses(true)"
          >
            <div style="min-width: 30px">
              <v-progress-circular
                v-if="isLoading"
                :size="25"
                width="2"
                color="primary"
                indeterminate
              />
              <v-icon
                v-else
              >
                mdi-refresh
              </v-icon>
            </div>
            Aktualisieren
          </v-btn>
        </div>
      </v-flex>
      <v-flex v-if="errorMessage">
        <AppToast
          :message="errorMessage"
          type="error"
        />
      </v-flex>
      <pageable-list
        :items="filteredProcesses"
        found-data-text="Vorgänge gefunden"
        no-data-text="Keine Vorgänge gefunden"
      >
        <template #default="props">
          <template v-for="item in props.items">
            <process-definition-item
              :key="item.key"
              :item="item"
              :search-string="filter || ''"
            />
          </template>
        </template>
      </pageable-list>
    </div>
  </app-view-layout>
</template>

<style scoped>


.searchField {
  margin: 1rem 0 1rem 0;
}
</style>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import AppToast from "@/components/UI/AppToast.vue";
import TaskItem from "@/components/task/TaskItem.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import {ServiceDefinitionTO} from '@muenchen/digiwf-engine-api-internal';
import ProcessDefinitionItem from "@/components/process/ProcessDefinitionItem.vue";
import AppPageableList from "@/components/UI/AppPageableList.vue";

@Component({
  components: {PageableList: AppPageableList, ProcessDefinitionItem, TaskItem, AppToast, AppViewLayout}
})
export default class Processes extends Vue {

  processDefinitions: ServiceDefinitionTO[] = [];
  isLoading = false;
  filter = "";
  errorMessage = "";

  created(): void {
    this.loadProcesses();
  }

  async loadProcesses(refresh = false): Promise<void> {
    this.processDefinitions = this.$store.getters['processDefinitions/processDefinitions'];
    this.isLoading = true;
    const startTime = new Date().getTime();
    try {
      await this.$store.dispatch('processDefinitions/loadProcessDefinitions', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
    setTimeout(() => this.isLoading = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  get filteredProcesses(): ServiceDefinitionTO[] {
    this.processDefinitions = this.$store.getters['processDefinitions/processDefinitions'];
    if (!this.filter) {
      return this.processDefinitions;
    }
    return this.processDefinitions.filter(task => JSON.stringify(Object.values(task)).toLocaleLowerCase().includes(this.filter.toLocaleLowerCase()));
  }


}
</script>
