import Ajv from "ajv";

/**
 * validates schema and parses types of input
 * @param schema
 * @param value
 */
export const validateSchema = (schema: any, value: any): any => {
  const ajv = new Ajv({coerceTypes: true, strict: false});
  ajv.validate(schema, value);
  return value;
};
