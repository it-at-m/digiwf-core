import { fileURLToPath, URL } from "node:url";

import { viteVueCESubStyle } from "@unplugin-vue-ce/sub-style";
import vue from "@vitejs/plugin-vue";
import { defineConfig, PluginOption } from "vite";
import cssInjectedByJsPlugin from "vite-plugin-css-injected-by-js";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue({
      customElement: true,
    }),
    viteVueCESubStyle() as PluginOption,
    cssInjectedByJsPlugin(),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
    extensions: [".js", ".json", ".jsx", ".mjs", ".ts", ".tsx", ".vue"],
  },
  build: {
    ssrManifest: true,
    manifest: true,
    minify: true,
    assetsDir: "src",
  },
  esbuild: {
    drop: ["console", "debugger"],
  },
  define: {
    "process.env": {},
  },
});
