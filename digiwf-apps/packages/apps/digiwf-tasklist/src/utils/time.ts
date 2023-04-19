import moment from "moment-timezone";
export const formatIsoDateTime = (isoDateTime: string) => moment(isoDateTime).format("DD.MM.YYYY, HH:mm"); // FIXME: add tests
export const formatIsoDate = (isoDateTime: string) => moment(isoDateTime).format("DD.MM.YYYY"); // FIXME: add tests
