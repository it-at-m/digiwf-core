import {createVuePlugin} from "vite-plugin-vue2";
import {defineConfig} from 'vite'
import Components from 'unplugin-vue-components/vite'
import {VuetifyResolver} from "unplugin-vue-components/resolvers";

export default defineConfig({
    plugins: [
        createVuePlugin(/* options */),
        Components({
            transformer: 'vue2',
            dts: true,
            resolvers: [
                VuetifyResolver()
            ]
        })
    ],
    server: {
        port: 8081
    },
    build: {
        commonjsOptions: {
            transformMixedEsModules: true,
        },
        minify: 'esbuild'
    }
})