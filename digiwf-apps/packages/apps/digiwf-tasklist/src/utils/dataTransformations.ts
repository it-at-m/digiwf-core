export function nullToUndefined<T = unknown>(variable?: T | null): T | undefined {
  return variable || undefined;
}
