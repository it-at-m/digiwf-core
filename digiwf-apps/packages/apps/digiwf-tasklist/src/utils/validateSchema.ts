import Ajv, { JSONSchemaType } from "ajv";

export type JSFValue = any; // eslint-disable-line

/**
 * validates schema and parses types of input
 * @param schema
 * @param value
 */
export const validateSchema = (
  schema: JSONSchemaType<JSFValue>,
  value: JSFValue
): JSFValue => {
  const ajv = new Ajv({
    coerceTypes: true,
    strict: false,
    // disable logging for missing types "time" and "date"
    logger: false,
  });
  ajv.validate(schema, value);
  return value;
};
