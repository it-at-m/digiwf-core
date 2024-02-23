import type { PageServiceInstanceTO } from "@muenchen/digiwf-engine-api-internal";

export type PageData = Pick<
  PageServiceInstanceTO,
  "totalPages" | "number" | "totalElements"
>;
