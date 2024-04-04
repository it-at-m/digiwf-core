export const checkRequired = (schema: any) => {
  return (
    schema["x-rules"]?.includes("required") ||
    schema["x-rules"]?.includes("requiredObject") ||
    schema.minLength > 0 ||
    schema.minItems > 0
  );
};
