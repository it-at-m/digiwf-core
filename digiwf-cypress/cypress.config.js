const { defineConfig } = require("cypress");

require("dotenv").config();
// load .env.local if .env doesn't exist or define anything
require("dotenv").config({ path: ".env.local" });

module.exports = defineConfig({
  viewportHeight: 1200,
  viewportWidth: 2000,
  scrollBehavior: "center",
  videosFolder: "output/videos",
  reporter: "cypress-multi-reporters",
  reporterOptions: {
    reporterEnabled: "mochawesome",
    mochawesomeReporterOptions: {
      reportDir: "output/reports/mocha",
      quite: true,
      overwrite: false,
      html: false,
      json: true,
    },
  },
  env: {
    sso_url: process.env.SSO_URL,
    sso_realm: process.env.SSO_REALM,
    sso_client: process.env.SSO_CLIENT,
    username: process.env.SSO_USERNAME,
    password: process.env.SSO_PASSWORD,
    user_realname: process.env.USER_REALNAME,
    user_group: process.env.USER_GROUP,
    user2: process.env.USER2,
    user2_realname: process.env.USER2_REALNAME,
    user2_group: process.env.USER2_GROUP,
  },
  e2e: {
    baseUrl: process.env.BASE_URL,
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
  chromeWebSecurity: false,
});
