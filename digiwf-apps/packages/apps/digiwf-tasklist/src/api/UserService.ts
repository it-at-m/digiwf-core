import {FetchUtils, UserTO} from '@muenchen/digiwf-engine-api-internal';

export default class UserService {
  //TODO wenn aufs neue API-Gateway umgestellt wird, hier auch den Endpoint umstellen
  private static base: string | undefined = "/api/sso/userinfo/";

  /**
   * Holt die Userdaten von der URL base.
   */
  static getUser(): Promise<UserTO> {
    return fetch(`${this.base}`, FetchUtils.getGETConfig())
      .catch(FetchUtils.defaultCatchHandler)
      .then((response) => {
        FetchUtils.defaultResponseHandler(
          response,
          `Beim laden des Users ist ein Fehler aufgetreten.`
        );
        return new Promise((resolve) => resolve(response.json()));
      });
  }
}
