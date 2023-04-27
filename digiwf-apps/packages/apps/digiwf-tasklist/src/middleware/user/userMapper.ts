import {UserResponse} from "../../api/user/userApiCalls";
import {User} from "./userModels";

export const mapUserResponse = (response: UserResponse):User => ({
  lhmObjectId: response.lhmObjectId,
  firstName: response.firstName,
  surname: response.surname,
  ou: response.ou,
  fullInfo: `${response.firstName} ${response.surname} (${response.ou})`
});
