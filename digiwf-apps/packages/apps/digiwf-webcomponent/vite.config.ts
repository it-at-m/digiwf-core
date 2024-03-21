import { fileURLToPath, URL } from "node:url";

import { viteVueCESubStyle } from "@unplugin-vue-ce/sub-style";
import vue from "@vitejs/plugin-vue";
import { defineConfig, loadEnv, PluginOption } from "vite";
import cssInjectedByJsPlugin from "vite-plugin-css-injected-by-js";

const portFromDevelopmentEnv = loadEnv("development", "./")?.VITE_PORT;
const port = portFromDevelopmentEnv
  ? Number.parseInt(portFromDevelopmentEnv)
  : 8081;

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue({
      customElement: true,
    }),
    viteVueCESubStyle() as PluginOption,
    cssInjectedByJsPlugin(),
  ],
  server: {
    port,
    proxy: {
      "/api": "http://localhost:8083/",
      "/actuator": "http://localhost:8083/",
      "/clients": "http://localhost:8083/",
    },
  },
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
    outDir: "dist",
    emptyOutDir: false,
    rollupOptions: {
      input: {
        "digiwf-service-instances-webcomponent":
          "./src/digiwf-service-instances-webcomponent.ts",
        "digiwf-hello-world-webcomponent":
          "./src/digiwf-hello-world-webcomponent.ts",
      },
      output: {
        entryFileNames: "entry-[name]-[hash].js",
        dir: "dist/src",
      },
    },
  },
  esbuild: {
    drop: ["console", "debugger"],
  },
  define: {
    "process.env": {},
  },
});
