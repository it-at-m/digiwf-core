describe('template spec', () => {
    it('passes', () => {
        cy.visit('https://portal-digiwf-test.apps.capk.muenchen.de/')
        cy.get("#username").type("");
        cy.get("#password").type("");
        cy.get('[id^=kc-login]').click();
    })
})