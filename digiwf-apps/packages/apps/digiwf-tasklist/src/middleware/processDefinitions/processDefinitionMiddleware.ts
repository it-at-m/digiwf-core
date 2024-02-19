import {Ref} from "vue";
import {useQuery} from "@tanstack/vue-query";
import {
  callGetProcessDefinition,
  callGetProcessDefinitionsFromEngine
} from "../../api/processDefinitions/processDefinitionApiCalls";
import {Page} from "../commonModels";
import {nullToUndefined} from "../../utils/dataTransformations";
import {ApiConfig} from "../../api/ApiConfig";
import {
  FetchUtils,
  ServiceDefinitionControllerApiFactory,
  ServiceDefinitionDetailTO
} from "@muenchen/digiwf-engine-api-internal";

export interface ProcessDefinition {
  readonly key: string;
  readonly name: string;
  readonly description?: string;
  readonly versionTag?: string;
}

export const useGetProcessDefinitions = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>) =>
  useQuery({
    queryKey: ["process-definitions", page.value, size.value, query.value || "no-query"],
    queryFn: () => {
      return callGetProcessDefinitionsFromEngine(page.value, size.value, nullToUndefined(query.value)) // remove null
        .then(data => {

          const content = data.content?.map<ProcessDefinition>(it => ({
            key: it.key || "", // FIXME: look if key could be really undefined
            name: it.name || "Unbekannt",
            description: it.description,
            versionTag: it.versionTag,
          })) ?? [];

          return Promise.resolve<Page<ProcessDefinition>>({
            content,
            page: data.number || 0,
            size: data.size || 0,
            totalPages: data.totalPages || 0,
            totalElements: data.totalElements
          });
        });
    },
  });


export interface LoadProcessResult {
  readonly data?: ServiceDefinitionDetailTO;
  readonly error?: string

}

export const loadProcess = (processKey: string): Promise<LoadProcessResult> => {
  return callGetProcessDefinition(processKey)
    .then((data): Promise<LoadProcessResult> => {
      return Promise.resolve({
        data: data,
        error: undefined
      });

    })
    .catch(() => {
      return Promise.resolve({
        data: undefined,
        error: "Der Vorgang konnte nicht geladen werden."
      });
    });
};
