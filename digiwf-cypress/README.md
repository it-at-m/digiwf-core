# Using Cypress tests

## Getting started

Download and install run:

```BASH
cd digiwf-cypress
npm install
```

For further information see:  https://docs.cypress.io/guides/getting-started/installing-cypress
It is important to use cypress in the `digiwf-cypress` folder.

## Run cypress

Navigate to the `digiwf-cypress` folder in the bash and run:

For lokal testing the Frontend needs to run behind the Gateway. To get this behaviour the frontend needs to be built and
started with `npm run preview:tasklist`.

```bash
cd digiwf-cypress
# set proxy (remote env) or unset proxy (local) via env variables if needed
export HTTP_PROXY=; export HTTPS_PROXY=; export NO_PROXY=
# setup .env file or needed env variables
npx cypress run # a) to run all the tests
npx cypress open # b) to open the cypress app and run each test separate
```

a) There will be a test summary in the end in the bash and the produced videos and test reports in the
digiwf-cypress/output folder.

b) The test with the test steps is shown in the Cypress App. The test will restart if elements
in the cypress test are changed and the results from the run before will be lost.
The test steps can be analysed by clicking in the corresponding line. The test flow is well comprehensible visible.

