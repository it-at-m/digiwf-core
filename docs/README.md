# DigiWF Docs

## With local Node

```bash
npm install
npm run dev
```

## Without local Node

Execute once to get local Node / NPM on your machine and install required modules:
```bash
mvn -f docs install -Pdocs
```

For the local development you can use: 
```bash
mvn -f docs -Pdocs -Pdev package
```
Open http://localhost:8080/ to serve the docs with life preview.



## Document new features and bugfixes

You should add new features to [src/features/index.md](src/features/index.md) with a title and a description 
that users can see all capabilities of the DigiWF plattform at once.

Additionally, you should add new features, bugfix and every other change to the list in [src/features/changes/index.md](src/features/changes/index.md).
This list may be published as release notes and is the summary of the CHANGELOG.md.

> Note: The CHANGELOG.md keeps track of all changes the DigiWF plattform is undergoing.
> The main peer group of the CHANGELOG.md are software developers that use (components of) the DigiWF plattform
> and need detailed information about every change.

### Adding element-templates and examples

You can add element-templates and example processes to [src/.vuepress/public](src/.vuepress/public)
and list them in the according files under [src/modeling/templates](src/modeling/templates).

### Adding new pages

If you want new pages to show up in the sidebar you have to add them in the [src/.vuepress/config.js](src/.vuepress/config.js).
See the [vuepress docs](https://v1.vuepress.vuejs.org/theme/default-theme-config.html#sidebar) for additional information. 
