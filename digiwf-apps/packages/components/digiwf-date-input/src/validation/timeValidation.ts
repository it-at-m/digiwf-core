import {DateTime} from "luxon";


export const ERROR_MESSAGES = {
  INVALID: "Ungültiges Zeitformat",
  MINUTES_TO_HIGH: "Minuten dürfen nicht höher als 59 sein.",
  HOURS_TO_HIGH: "Stunden dürfen nicht höher als 24 sein.",
}

export const validateTime = (time: string = "", nativeValidationResult: boolean = true): string | boolean => {
  // if date is empty and html native input validation was successfully
  if (time.trim().length === 0 && nativeValidationResult) {
    return true
  }

  const dateObject: any = DateTime.fromFormat(time, "HH:mm"); // types for invalid does not fit
  if (isNaN(dateObject)) {
    /*
    really dirty solution for parsing error from:
    https://github.com/moment/luxon/blob/master/src/impl/conversions.js#L15
     */
    if (dateObject.invalid.reason === "unit out of range") {
      if (dateObject.invalid.explanation.includes("as a minute, which is invalid")) {
        return ERROR_MESSAGES.MINUTES_TO_HIGH;
      }
      if (dateObject.invalid.explanation.includes("as a hour, which is invalid")) {
        return ERROR_MESSAGES.HOURS_TO_HIGH;
      }
    }
    return ERROR_MESSAGES.INVALID;
  }
  return true;
}
