const { defineConfig } = require("cypress");

module.exports = defineConfig({
  env: {
    username: "user",
    password: "pw"
  },
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
