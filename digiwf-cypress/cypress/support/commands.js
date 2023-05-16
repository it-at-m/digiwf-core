// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })

Cypress.Commands.add("loginUser", () => {
    const username = Cypress.env('username');
    const password = Cypress.env('password');
    const home = Cypress.env('home');
    //login
    cy.visit(home)
    cy.get("#username").type(username);
    cy.get("#password").type(password);
    cy.get('[id^=kc-login]').click();
})

Cypress.Commands.add("openVorgangStarten", () => {
    cy.intercept({
        method: 'GET',
        url: '/api/digitalwf-backend-service/rest/service/definition',
    }).as('dataGetAntraege');
    cy.get('a.v-list-item:nth-child(5) > div:nth-child(1)').click();
    cy.wait('@dataGetAntraege').its('response.statusCode').should('equal', 200);
})

Cypress.Commands.add("clickSingleButton", () => {
    const singleButton = 'div.buttonGroup:nth-child(1)'
    cy.intercept({
        method: 'GET',
        url: '/api/digitalwf-backend-service/rest/filter',
    }).as('dataGetFilter');
    cy.get(singleButton).click();
    cy.wait('@dataGetFilter').its('response.statusCode').should('equal', 200);
})
