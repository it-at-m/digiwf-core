<script>
import pkg from '../../package.json'
import {defineComponent} from '@vue/composition-api'

export default defineComponent({
  setup() {
    const {description, version} = pkg

    const items = [
      {name: 'home', label: 'Home'},
      {name: 'customers', label: 'Customers'},
      {name: 'inventory', label: 'Inventory'}
    ]

    return {
      description,
      version,
      items
    }
  }
})

</script>

<template>
  <nav>
    <div v-for="item in items" :key="item.name"
         :to="{ name: item.name }">
      {{ item.label }}
    </div>

    <aside>
      {{ description }} v.{{ version }}
    </aside>
  </nav>
</template>

<style scoped lang="scss">
nav {
  flex: 0;
  display: flex;
  flex-direction: row;
  padding: 24px;
  background-color: #1f3763;
  color: white;

  a {
    flex: 0;
    margin-right: 32px;
    text-decoration: none;
    color: #dddddd;
    font-size: 18px;
    font-weight: 100;
    outline: none;

    &:hover {
      color: orange;
    }
  }

  aside {
    flex: 1;
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
  }
}
</style>
