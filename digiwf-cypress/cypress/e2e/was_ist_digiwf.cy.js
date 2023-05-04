describe('template spec', () => {
    it('passes', () => {
        const username = Cypress.env('username')
        const password = Cypress.env('password')


        cy.visit('https://portal-digiwf-test.apps.capk.muenchen.de/')
        cy.get("#username").type(username);
        cy.get("#password").type(password);
        cy.get('[id^=kc-login]').click();
        cy.get("#suchfeld").click();
        cy.get("#suchfeld").type("DigiWftbhenjdtjshergrntujdtjhsrvgaebn");
    })
})