
// only for more readable application code
export type VJSFConfig = any;

/**
 * config for vuetify-json-schema-form
 *
 * Link to configuration:
 * https://koumoul-dev.github.io/vuetify-jsonschema-form/latest/configuration
 */
export const defaultVJFSConfig = {
  locale: 'de',
  markdownit: {breaks: true},
  /**
   * important: Against the of official documentation
   * you have to set it to false if you want to use optional input groups
   */
  removeAdditionalProperties: false
};
