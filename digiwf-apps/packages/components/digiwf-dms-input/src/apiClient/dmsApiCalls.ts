import {Configuration, MetadataApiFactory, Metadata} from "@muenchen/digiwf-dms-api-internal";
import {AxiosResponse} from "axios";
import {Objectclass} from "@/types";

/**
 * Calls readMetadata on dms api
 * @param cfg Provides security configuration and interface parameters
 * @param ojectclass Objectclass of dms object
 * @param coo Coo of dms object
 */
export const getMetadataFromDmsservice = (ojectclass: Objectclass, coo: string, cfg: Configuration): Promise<Metadata> => {
  return MetadataApiFactory(cfg)
    .readMetadata(ojectclass,coo)
    .then((response: AxiosResponse<Metadata>) => Promise.resolve(response.data));
}

