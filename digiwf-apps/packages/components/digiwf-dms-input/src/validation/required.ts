export const checkRequired = (schema: any) => {
  return schema['x-rules']?.includes('required') ||
    schema.minLength > 0 ||
    schema.minItems > 0;
};
