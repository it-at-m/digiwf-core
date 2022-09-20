<template>
  <v-data-iterator
    class="dataContainer"
    :items="items"
    :items-per-page.sync="itemsPerPage"
    :page="page"
    :no-data-text="noDataText"
    hide-default-footer
  >
    <template #default="props">
      <v-list>
        <slot :items="props.items" />
      </v-list>
    </template>

    <template #footer>
      <v-row
        v-if="items.length >= 10"
        class="ma-1 mt-3"
        align="center"
        justify="center"
      >
        <span>Seitengröße</span>
        <v-menu offset-y>
          <template #activator="{ on, attrs }">
            <v-btn
              text
              outlined
              color="primary"
              class="ml-2 pa-1"
              v-bind="attrs"
              v-on="on"
            >
              <span class="mr-1">{{ itemsPerPage }}</span>
              <v-icon>mdi-chevron-down</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(number, index) in itemsPerPageArray"
              :key="index"
              @click="updateItemsPerPage(number)"
            >
              <v-list-item-title>
                {{ number }}
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
        <v-spacer />
        <span class=" mr-1">{{ items.length }} {{ foundDataText }}
        </span>
        <v-spacer />

        <span
          class="mr-4"
        >
          Seite {{ page }} von {{ numberOfPages }}
        </span>
        <v-tooltip
          top
          open-delay="700"
        >
          <template #activator="{ on, attrs }">
            <v-btn
              :disabled="lastPageButtonDisabled"
              small
              text
              color="primary"
              class="mr-1"
              v-bind="attrs"
              v-on="on"
              @click="lastPage"
            >
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
          </template>
          <span>Vorherige Seite</span>
        </v-tooltip>
        <v-tooltip
          top
          open-delay="700"
        >
          <template #activator="{ on, attrs }">
            <v-btn
              small
              text
              :disabled="nextPageButtonDisabled"
              color="primary"
              class="ml-1"
              v-bind="attrs"
              v-on="on"
              @click="nextPage"
            >
              <v-icon>mdi-chevron-right</v-icon>
            </v-btn>
          </template>
          <span>Nächste Seite</span>
        </v-tooltip>
      </v-row>
    </template>
  </v-data-iterator>
</template>
<style scoped>
.list {
  overflow: auto;
  max-height: calc(100vh - 19rem);
}
</style>

<script lang="ts">

import {Component, Prop, Vue, Watch} from "vue-property-decorator";

@Component
export default class AppPageableList extends Vue {

  itemsPerPageArray = [10, 25, 50];
  itemsPerPage = 10;
  page = 1;

  @Prop()
  items!: any[];

  @Prop()
  noDataText!: string;

  @Prop()
  foundDataText!: string;

  get numberOfPages(): number {
    return Math.ceil(this.items.length / this.itemsPerPage);
  }

  updateItemsPerPage(number: number): void {
    this.itemsPerPage = number;
  }

  nextPage(): void {
    if (this.page + 1 <= this.numberOfPages) this.page += 1;
  }

  lastPage(): void {
    if (this.page - 1 >= 1) this.page -= 1;
  }

  get nextPageButtonDisabled(): boolean {
    return this.page >= this.numberOfPages;
  }

  get lastPageButtonDisabled(): boolean {
    return this.page <= 1;
  }

  @Watch("numberOfPages")
  updatePageNumber(numberOfPages: number) {
    if (numberOfPages < this.page) {
      this.page = numberOfPages;
    }
    if (this.page == 0 && numberOfPages > 0) {
      this.page = 1;
    }

    if (numberOfPages == 0) {
      this.page = 0;
    }
  }
}
</script>

