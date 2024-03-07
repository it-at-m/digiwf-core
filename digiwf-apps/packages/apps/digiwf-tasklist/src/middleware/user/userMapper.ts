import { UserProfile } from "@muenchen/digiwf-task-api-internal";

import { User } from "./userModels";

export const mapUserResponse = (response: UserProfile): User => ({
  lhmObjectId: response.userId,
  firstName: response.firstName,
  surname: response.lastName,
  ou: response.primaryOrgUnit,
  fullInfo: `${response.firstName} ${response.lastName} (${response.primaryOrgUnit})`,
});
