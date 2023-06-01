const { defineConfig } = require("cypress");

module.exports = defineConfig({
  viewportHeight: 1200,
  viewportWidth: 2000,
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
    home: "localhost:8082"
  },
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
  chromeWebSecurity:true
});
