import {Configuration, MetadataApiFactory, Metadata} from "@muenchen/digiwf-dms-api-internal";
import {AxiosResponse} from "axios";
import {Objectclass} from "../../types";

/**
 * @param cfg
 * @param ojectclass
 * @param coo
 */
export const getMetadataFromDmsservice = (cfg: Configuration, ojectclass: Objectclass, coo: string): Promise<Metadata> => {
  return MetadataApiFactory(cfg).readMetadata(ojectclass,coo)
    .then((response: AxiosResponse<string[]>) => Promise.resolve(response.data));
}

