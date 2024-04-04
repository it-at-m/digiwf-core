import { UserTO } from "@muenchen/digiwf-engine-api-internal";
import { UserProfile } from "@muenchen/digiwf-task-api-internal";

import { User } from "./userModels";

export const mapUserResponse = (response: UserProfile): User => ({
  lhmObjectId: response.userId,
  firstName: response.firstName,
  surname: response.lastName,
  ou: response.primaryOrgUnit,
  fullInfo: `${response.firstName} ${response.lastName} (${response.primaryOrgUnit})`,
});

export const mapUserTOToUser = (response: UserTO): User => ({
  lhmObjectId: response.lhmObjectId || "-",
  firstName: response.forename || "-",
  surname: response.surname || "-",
  ou: response.ou || "-",
  fullInfo: `${response.forename} ${response.surname} (${response.ou})`,
});
