import {createVuePlugin} from "vite-plugin-vue2";
import {defineConfig} from 'vite'
import {fileURLToPath, URL} from "url";

export default defineConfig({
    plugins: [
        createVuePlugin(),
    ],
    build: {
        target: 'esnext',
        lib: {
            entry: 'src/index.ts',
            name: 'digiwf-form-renderer',
        },
        rollupOptions: {
            external: [
                'vue',
                'vuex',
                'vue-router',
                /@vuetify\/.*/,
                /@vue\/.*/,
                /@koumoul\/.*/
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
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
})