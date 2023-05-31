const LOCAL_STORAGE_USE_TASKSERVICE_KEY = "FEATURE_USE_TASK_SERVICE";
const COOKIE_USE_TASKSERVICE_KEY = "FEATURE_USE_TASK_SERVICE";
const COOKIE_SHOW_BETA_BUTTON_KEY = "FEATURE_SHOW_BETA_BUTTON";

/**
 * if FEATURE_USE_TASK_SERVICE cookie is set to true, the taskservice will allways used.
 * Otherwise when the FEATURE_SHOW_BETA_BUTTON cookie is set to true and the users activated the feature, the taskservice
 */
export const shouldUseTaskService = (): boolean => {
  const useTaskServiceCookieValue: boolean = getCookie(COOKIE_USE_TASKSERVICE_KEY).trim().toLowerCase() === "true";
  const showBetaButtonCookieValue: boolean = getCookie(COOKIE_SHOW_BETA_BUTTON_KEY).trim().toLowerCase() === "true";
  const isTaskServiceActivatedInLocalStorage = localStorage.getItem(LOCAL_STORAGE_USE_TASKSERVICE_KEY)?.toLocaleLowerCase().trim() === "true";

  if (useTaskServiceCookieValue && isTaskServiceActivatedInLocalStorage) {
    return true;
  }
  return showBetaButtonCookieValue && isTaskServiceActivatedInLocalStorage;
}

export const setShouldUseTaskService = (newValue: boolean) => {
  localStorage.setItem(LOCAL_STORAGE_USE_TASKSERVICE_KEY, newValue ? "true" : "false")
}

export const shouldShowBetaButton = (): boolean => {
  return getCookie(COOKIE_SHOW_BETA_BUTTON_KEY).trim().toLowerCase() === "true";
}

/**
 * returns the value of the cookie
 * copied from https://www.w3schools.com/js/js_cookies.asp
 * @param cname
 */
function getCookie(cname: string): string {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}
