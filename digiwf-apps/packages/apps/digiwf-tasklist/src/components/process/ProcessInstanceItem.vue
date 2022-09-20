<template>
  <div>
    <v-list-item
      :aria-label="'Vorgang '+item.definitionName+ ' öffnen'"
      class="d-flex justify-space-between"
      :to="'/instance/'+item.id"
    >
      <v-flex
        class="d-flex flex-column processColumn"
        style="min-height: 4.5rem; max-height: 6rem; margin: 8px 0"
      >
        <h2 class="processTitle">
          <text-highlight :queries="searchString">
            {{ item.definitionName }}
          </text-highlight>
        </h2>
        <span>
          <text-highlight :queries="searchString"> {{ item.description }} </text-highlight>
        </span>
      </v-flex>
      <v-flex
        style="min-width: 150px; max-width: 150px"
        class="processColumn"
      >
        <span>
          <text-highlight :queries="searchString"> {{ item.status }}</text-highlight>
        </span>
      </v-flex>
      <v-flex
        style="min-width: 100px; max-width: 100px"
        class="processColumn"
      >
        <span class="taskInfo">
          {{ createdAt }}
        </span>
      </v-flex>
      <div
        class="ml-2"
        style="min-width: 30px; max-width: 30px"
      >
        <v-menu
          top
          offset-x
        >
          <template #activator="{ on, attrs }">
            <v-btn
              icon
              v-bind="attrs"
              @click="(event) => { event.preventDefault()}"
              v-on.prevent="on"
            >
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              link
              :to="'/instance/'+item.id"
              @click="(event) => { event.preventDefault()}"
            >
              <v-list-item-title>Anzeigen</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </v-list-item>
    <hr class="hrDivider">
  </div>
</template>

<style scoped>

.processColumn {
  margin: 0 0 0 5px;
  align-self: baseline;
  overflow: hidden;
}

.taskInfo {
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  margin-bottom: 0.3rem;
}

.processTitle {
  font-size: 1.2rem;
}
</style>

<script lang="ts">
import {Component, Emit, Prop, Vue} from "vue-property-decorator";
import {ServiceInstanceTO} from '@muenchen/digiwf-engine-api-internal';
import {DateTime} from "luxon";

@Component
export default class ProcessDefinitionItem extends Vue {

  @Prop()
  item!: ServiceInstanceTO;

  @Prop()
  searchString!: string;

  @Emit("on-click")
  onClick(): string {
    return this.item.id!;
  }

  get createdAt(): string {
    return DateTime.fromISO(this.item.startTime!).toLocaleString(DateTime.DATETIME_SHORT);
  }

}
</script>
