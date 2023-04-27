

export interface UserResponse {
  readonly lhmObjectId: string;
  readonly firstName: string,
  readonly surname: string,
  readonly ou: string
}
export const callGetUserInfoFromTaskService = (id: string): Promise<UserResponse> => {
  console.log("mock user info request call", id)
  return Promise.resolve({
    firstName: "firstName",
    lhmObjectId: "123456789",
    ou: "ou",
    surname: "surname"
  })
}
