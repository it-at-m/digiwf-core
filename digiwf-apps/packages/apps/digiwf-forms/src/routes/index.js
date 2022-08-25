import { createWebHistory, createRouter } from 'vue-router'
import customerRoutes from '@vue-vite-monorepo/customers.ui'
import inventoryRoutes from '@vue-vite-monorepo/inventory.ui'
import Home from '../views/home.vue'

/**
 * UI routes
 */
const routes = [
  // General routes
  {
    path: '/',
    name: 'home',
    component: Home
  },

  // Routes introduced by `customers.ui` package
  ...customerRoutes,

  // Routes introduced by `inventory.ui` package
  ...inventoryRoutes
]

/**
 * Creates UI router with all the UI routes
 */
export default createRouter({
  history: createWebHistory(),
  routes
})
