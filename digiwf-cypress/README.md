# Using Cypress tests

## Getting started

Download and install run:
```BASH
npm install
```
For further information see:  https://docs.cypress.io/guides/getting-started/installing-cypress
It is important to use cypress in the digiwf-cypress folder. 

## Run a cypress test

Navigate to the digiwf-cypress folder in the bash and run:

```bash
npx cypress run # a) to run all the tests
npx cypress open # b) to open the cypress app and run each test separate
```
a) There will be a test summary in the end in the bash and the produced videos and test reports in the 
digiwf-cypress/output folder. 

b) The test with the test steps is shown in the Cypress App. The test will restart if elements 
in the cypress test are changed and the results from the run before will be lost. 
The test steps can be analysed by clicking in the corresponding line. The test flow is well comprehensible visible.

