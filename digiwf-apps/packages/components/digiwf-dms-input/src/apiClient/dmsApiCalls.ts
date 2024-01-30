import {Configuration, MetadataApiFactory, Metadata} from "@muenchen/digiwf-dms-api-internal";
import {AxiosResponse} from "axios";
import {Objectclass} from "../types";

/**
 * @param cfg
 * @param ojectclass
 * @param coo
 */
export const getMetadataFromDmsservice = (ojectclass: Objectclass, coo: string, cfg: Configuration): Promise<Metadata> => {
  return MetadataApiFactory(cfg)
    .readMetadata(ojectclass,coo)
    .then((response: AxiosResponse<Metadata>) => Promise.resolve(response.data));
}

