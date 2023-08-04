<template>
  <v-select
    v-model="value"
    :items="items"
    class="sortBySelect"
    label="Sortierung"
    :flat="true"
    outlined
  />
</template>
<script lang="ts">
import {defineComponent, ref, watch} from "vue";
import {usePageFilters, useUpdatePageFilters} from "../../store/modules/filters";

export default defineComponent({
  props: {},
  setup() {
    const pageFilters = usePageFilters();
    const setFiltersOfPage = useUpdatePageFilters();
    const sortDirection = ref<string>(pageFilters.current.value.sortDirection);
    watch(sortDirection, (value) => {
      setFiltersOfPage({sortDirection: value});

    });
    return {
      items: pageFilters.sortDirections,
      value: sortDirection
    };
  }
});
</script>

<style scoped>
.sortBySelect {
  margin: 1rem 0 1rem 0;
}
</style>
