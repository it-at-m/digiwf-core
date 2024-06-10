<template>
  <v-row
    align="center"
    class="ma-1 mt-3"
    data-test="pagination-footer"
    justify="center"
  >
    <span>Seitengröße</span>
    <v-menu
      offset-y
    >
      <template #activator="{ on, attrs }">
        <v-btn
          class="ml-2 pa-1"
          color="primary"
          data-test="pagination-page-size-btn"
          outlined
          text
          v-bind="attrs"
          v-on="on"
        >
          <span class="mr-1">{{ size }}</span>
          <v-icon>mdi-chevron-down</v-icon>
        </v-btn>
      </template>
      <v-list data-test="pagination-page-select">
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
    <span
      class="mr-1"
      data-test="pagination-item-count"
    >{{ totalNumberOfItems }} {{ foundDataText }}</span>
    <v-spacer />

    <span
      class="mr-4"
      data-test="pagination-page-index"
    >Seite {{ page }} von {{ numberOfPages }}</span>
    <v-tooltip
      open-delay="700"
      top
    >
      <template #activator="{ on, attrs }">
        <v-btn
          :disabled="lastPageButtonDisabled"
          aria-label="Vorherige Seite"
          class="mr-1"
          color="primary"
          data-test="pagination-previous-page"
          small
          text
          v-bind="attrs"
          @click="lastPage"
          v-on="on"
        >
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>
      </template>
      <span>Vorherige Seite</span>
    </v-tooltip>
    <v-tooltip
      open-delay="700"
      top
    >
      <template #activator="{ on, attrs }">
        <v-btn
          :disabled="nextPageButtonDisabled"
          aria-label="Nächste Seite"
          class="ml-1"
          color="primary"
          data-test="pagination-next-page"
          small
          text
          v-bind="attrs"
          @click="nextPage"
          v-on="on"
        >
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>
      </template>
      <span>Nächste Seite</span>
    </v-tooltip>
  </v-row>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  components: {},
  props: {
    numberOfPages: {type: Number, required: true},
    totalNumberOfItems: {type: Number, required: true},
    page: {type: Number, required: true},
    size: {type: Number, required: true},
    foundDataText: {type: String, required: true},
    nextPageButtonDisabled: {type: Boolean, required: true},
    lastPageButtonDisabled: {type: Boolean, required: true},
    updateItemsPerPage: {type: Function, required: true},
    nextPage: {type: Function, required: true},
    lastPage: {type: Function, required: true},
  },
  setup() {
    return {
      itemsPerPageArray: [5, 10, 20],
    };
  },
});
</script>

<style scoped></style>
