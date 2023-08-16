<template>
  <v-select
    v-model="value"
    :items="items"
    class="sortBySelect"
    label="Sortierung"
    data-cy="sort-field"
    flat
    outlined
    dense
    hide-details
    style="max-width: 250px"
  />
</template>
<script lang="ts">
import {defineComponent} from "vue";
import {usePageFilters} from "../../store/modules/filters";

export default defineComponent({
  props: {},
  setup() {
    const {currentSortDirection, sortDirections} = usePageFilters();
    return {
      items: sortDirections,
      value: currentSortDirection
    };
  }
});
</script>

<style scoped>
.sortBySelect {
  margin: 1rem 0 1rem 0;
}
</style>
