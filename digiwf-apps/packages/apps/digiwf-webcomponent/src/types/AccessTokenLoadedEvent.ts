type AccessTokenLoadedEventData = {
  accessToken: string;
};

export type AccessTokenLoadedEvent = CustomEvent<AccessTokenLoadedEventData>;
