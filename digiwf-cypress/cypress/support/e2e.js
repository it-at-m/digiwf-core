// ***********************************************************
// This example support/e2e.js is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './commands'
import 'cypress-keycloak'

// Alternatively you can use CommonJS syntax:
// require('./commands')

const DEFAULT_DELAY = 500

function setupDefaultIntercept(req) {
    req.continue((res) => {
        res.setDelay(DEFAULT_DELAY)
    })
}

beforeEach(() => {
    cy.intercept({
        method: 'GET',
        url: '/api/digitalwf-tasklist-service/rest/tasks/user*',
    }, setupDefaultIntercept).as('dataGetMyTasks')
    cy.intercept({
        method: 'GET',
        url: '/api/digitalwf-backend-service/rest/service/instance*',
    }, setupDefaultIntercept).as('dataGetInstances')
    cy.intercept({
        method: 'GET',
        url: '/api/digitalwf-backend-service/rest/service/definition*',
    }, setupDefaultIntercept).as('dataGetDefinitions')
    cy.intercept({
        method: 'GET',
        url: '/api/digitalwf-tasklist-service/rest/tasks/group/unassigned*',
    }, setupDefaultIntercept).as('dataGetOpenGroupTasks')
    cy.intercept({
        method: 'GET',
        url: '/api/digitalwf-tasklist-service/rest/tasks/group/assigned*',
    }, setupDefaultIntercept).as('dataGetAssignedGroupTasks')
    cy.intercept({
        method: 'POST',
        url: '/api/digitalwf-backend-service/rest/user/search',
    }, setupDefaultIntercept).as('dataUserSearch')
})
