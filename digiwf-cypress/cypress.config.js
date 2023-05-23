const { defineConfig } = require("cypress");

module.exports = defineConfig({
  videosFolder: "output/videos",
  reporter: "cypress-multi-reporters",
  reporterOptions: {
    reporterEnabled: "mochawesome",
    mochawesomeReporterOptions: {
      reportDir: "output/reports/mocha",
      quite: true,
      overwrite: false,
      html: false,
      json: true
    }
  },
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
