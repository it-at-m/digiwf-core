## Getting started

- ``npm run init``
- ``npm run build``
- ``npm run serve:forms``

## Release

- run ``npm run new-version`` locally
- check in
- create tag with `app_` prefix in GitHub

## Roadmap

- migrate digiwf-frontend

## Development

To simplify development we provided a few shortcut commands to rebuild components on change and to run the applications

```bash
# Rebuild components on change
npm run dev

# Run all applications from packages/apps
npm run serve

# Run a specific application from packages/apps
npm run serve:forms
```

The `npm run dev` command is a shortcut for `npx lerna run watch --stream --parallel` command.
If you don't want to watch for changes in all components you can add the `--scope` flag to the command,
e.g. `npx lerna run watch --stream --scope=@muenchen/digiwf-multi-file-input`.

To run the dev servers for the application you can use the `npm run serve` command.
If you don't want to serve all applications with one command you can use the helper commands for each app `npm run serve:forms`.
