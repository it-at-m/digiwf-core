const { defineConfig } = require("cypress");

module.exports = defineConfig({
  env: {
    username: "user",
    password: "pw",
    home: "https://portal-digiwf-test.apps.capk.muenchen.de/#/mytask"
  },
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
  chromeWebSecurity:true
});
