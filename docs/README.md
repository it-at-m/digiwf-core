# DigiWF Docs

## With local Node

```bash
npm install
npm run dev
```

## Without local Node

Execute once to get local Node / NPM on your machine and install the required modules:

```bash
./mvnw -f docs install -Pdocs
```

For local development, you can use:

```bash
./mvnw -f docs -Pdocs -Pdev package
```

Open http://localhost:8080/ to serve the docs with live preview.

### Adding element-templates and examples

You can add element-templates and example processes to [src/.vuepress/public](src/.vuepress/public) and list them in the
respective files
under [src/modeling/templates](src/modeling/templates).

### Adding new pages

If you want new pages to show up in the sidebar, you have to add them
in [src/.vuepress/config.js](src/.vuepress/config.js). See
the [vuepress docs](https://v1.vuepress.vuejs.org/theme/default-theme-config.html#sidebar) for additional information.