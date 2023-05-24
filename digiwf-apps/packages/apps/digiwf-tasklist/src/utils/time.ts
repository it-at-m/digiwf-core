import {DateTime} from "luxon";

export const formatIsoDateTime = (isoDateTime: string) => DateTime
  .fromISO(isoDateTime)
  .setLocale("de")
  .setZone("Europe/Berlin")
  .toLocaleString({
    ...DateTime.DATETIME_SHORT,
    day: "2-digit",
    month: "2-digit"
  });
export const formatIsoDate = (isoDateTime: string) => DateTime
  .fromISO(isoDateTime)
  .setLocale("de")
  .toLocaleString({
    ...DateTime.DATE_SHORT,
    day: "2-digit",
    month: "2-digit"
  });

export const getCurrentDate = () => DateTime
  .now()
  .toISODate();

/**
 *
 * @param date format: YYYY.MM.DD
 */
export const dateToIsoDateTime = (date: string): string => DateTime
  .fromFormat(date, "yyyy.MM.dd", {locale: "de", zone: "Europe/Berlin"})
  .toISO();
