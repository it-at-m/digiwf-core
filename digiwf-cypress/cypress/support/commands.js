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

    //Cypress.Cookies.debug(true, { verbose: true })
    cy.session(
        username, cy.login,
            {
                validate () {
                    cy.getCookies().should('exist');
                },
                cacheAcrossSpecs: true
            }
    ).then(async () => {
             const sessionData = await Cypress.session.getCurrentSessionData()
         })
    cy.visit(Cypress.env('home'))
});

Cypress.Commands.add("login", () => {
    const username = Cypress.env('username');
    const password = Cypress.env('password');
    const home = Cypress.env('home');
            cy.visit(home);
            cy.get("#username").type(username);
            cy.get("#password").type(password);
            cy.get('[id^=kc-login]').click();
            if(home.toString().includes("local")){
                // Only necessary for local, since slightly different keycloak setup
                cy.contains("http://keycloak:8080/auth/realms/P82").click();
            }
});


