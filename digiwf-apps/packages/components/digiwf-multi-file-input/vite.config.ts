import {createVuePlugin} from "vite-plugin-vue2";
import {defineConfig} from 'vite'
//@ts-ignore
import {fileURLToPath, URL} from "url";
import Components from 'unplugin-vue-components/vite'
import {VuetifyResolver} from "unplugin-vue-components/resolvers";

export default defineConfig({
    plugins: [
        createVuePlugin(),
        Components({
            transformer: 'vue2',
            dts: true,
            resolvers: [
                VuetifyResolver()
            ]
        })
    ],
    build: {
        lib: {
            entry: 'src/index.ts',
            name: 'digiwf-multi-file-input',
        },
        rollupOptions: {
            external: [
                'vue',
                'vuex',
                'vue-router',
                /@vuetify\/.*/
            ],
            output: {
                globals: {
                    vue: 'Vue'
                }
            }
        },
        minify: 'esbuild'
    },
    resolve: {
        alias: {
            //@ts-ignore
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
})
