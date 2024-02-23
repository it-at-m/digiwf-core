import type {
  PageServiceInstanceTO,
  ServiceInstanceTO,
} from "@muenchen/digiwf-engine-api-internal";

const SERVICE_INSTANCES_DUMMIES: ServiceInstanceTO[] = [
  {
    id: "fc4bf2e6-9ced-4ce3-b5b4-1140a4591ea1",
    definitionName: "Mobiles Arbeiten beantragen",
    startTime: "2023-11-02T11:15:31.000+00:00",
    endTime: null,
    status: "Antrag geprueft",
    description: "Das ist eine Beschreibung",
  },
  {
    id: "c1da85ea-8532-4cfe-9774-0bbb6576c2f0",
    definitionName: "Fahrkostenzuschuss erstmalig beantragen",
    startTime: "2023-09-02T11:15:31.000+00:00",
    endTime: "2023-10-02T11:15:31.000+00:00",
    status: "Antrag geprueft",
    description: "Das ist eine Beschreibung",
  },
  {
    id: "fc4bf2e6-9ced-4ce3-b5b4-1140a4591ea1",
    definitionName: "Mobiles Arbeiten beantragen",
    startTime: "2023-11-02T11:15:31.000+00:00",
    endTime: null,
    status: "Antrag geprueft",
    description: "Das ist eine Beschreibung",
  },
];

const PAGE_SERVICE_INSTANCE_DUMMY: PageServiceInstanceTO = {
  content: SERVICE_INSTANCES_DUMMIES,
  totalPages: 2,
  totalElements: 6,
};

export default PAGE_SERVICE_INSTANCE_DUMMY;
