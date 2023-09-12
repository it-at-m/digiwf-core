import {DateTime} from "luxon";


export const validateDate = (date: string, nativeValidationResult: boolean = true) : string | boolean => {
  // if date is empty and html native input validation was successfully
  if(date.trim().length === 0 && nativeValidationResult) {
    return true
  }
  // 5 times 'y' is correct in that case: https://moment.github.io/luxon/docs/manual/parsing.html#table-of-tokens
  const dateObject = DateTime.fromFormat(date, "yyyyy-MM-dd");
  if(isNaN(dateObject as any)) {
    return  "Ungültiges Datumsformat"
  }
  if(dateObject.get("year") > 9999) {
    return "Datum darf nicht mehr als 4 Jahreszahlen enthalten"
  }
  return true;
}
