import {AxiosError} from "axios";


export interface ApiCallError {
  readonly message: string
  readonly status?: number
}

export const defaultApiErrorHandler = (err: AxiosError): ApiCallError => {
  const error: ApiCallError = {
    status: err.response?.status,
    message: (err.response?.data as any)?.error || "unknown error"
  };
  return error;
};
