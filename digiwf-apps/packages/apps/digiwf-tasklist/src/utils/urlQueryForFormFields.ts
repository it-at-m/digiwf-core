export const parseQueryParameterInputs = (inputs?: string): any => {
  if (inputs) {
    try {
      return JSON.parse(inputs);
    } catch (_) {
      return {};
    }
  }
  return {};
};

