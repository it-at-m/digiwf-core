package io.muenchendigital.digiwf.cosys.integration.gen.api;

import io.muenchendigital.digiwf.cosys.integration.gen.ApiClient;

import io.muenchendigital.digiwf.cosys.integration.gen.model.CosysUrlParameter;
import io.muenchendigital.digiwf.cosys.integration.gen.model.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class DefaultApi {
    private ApiClient apiClient;

    public DefaultApi() {
        this(new ApiClient());
    }

    @Autowired
    public DefaultApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Analyzes a template for used fields and other information
     * 
     * <p><b>200</b> - Used fields and other information of the template
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param mode The type of the template.
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @return Object
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec analyseTemplateRequestCreation(String guid, String client, String role, String mode, String stateFilter, String validity) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'guid' is set
        if (guid == null) {
            throw new WebClientResponseException("Missing the required parameter 'guid' when calling analyseTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling analyseTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling analyseTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'mode' is set
        if (mode == null) {
            throw new WebClientResponseException("Missing the required parameter 'mode' when calling analyseTemplate", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        pathParams.put("guid", guid);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "client", client));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "role", role));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "mode", mode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stateFilter", stateFilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validity", validity));

        final String[] localVarAccepts = { 
            "application/json;mode=DATA_SUPPLY", "application/json;mode=FIELD_DEFINITION", "application/json;mode=SYNTAX"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<Object>() {};
        return apiClient.invokeAPI("/analyseTemplate/{guid}", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Analyzes a template for used fields and other information
     * 
     * <p><b>200</b> - Used fields and other information of the template
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param mode The type of the template.
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @return Object
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<Object> analyseTemplate(String guid, String client, String role, String mode, String stateFilter, String validity) throws WebClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<Object>() {};
        return analyseTemplateRequestCreation(guid, client, role, mode, stateFilter, validity).bodyToMono(localVarReturnType);
    }

    /**
     * Analyzes a template for used fields and other information
     * 
     * <p><b>200</b> - Used fields and other information of the template
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param mode The type of the template.
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @return ResponseEntity&lt;Object&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<Object>> analyseTemplateWithHttpInfo(String guid, String client, String role, String mode, String stateFilter, String validity) throws WebClientResponseException {
        ParameterizedTypeReference<Object> localVarReturnType = new ParameterizedTypeReference<Object>() {};
        return analyseTemplateRequestCreation(guid, client, role, mode, stateFilter, validity).toEntity(localVarReturnType);
    }

    /**
     * Analyzes a template for used fields and other information
     * 
     * <p><b>200</b> - Used fields and other information of the template
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param mode The type of the template.
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec analyseTemplateWithResponseSpec(String guid, String client, String role, String mode, String stateFilter, String validity) throws WebClientResponseException {
        return analyseTemplateRequestCreation(guid, client, role, mode, stateFilter, validity);
    }
    /**
     * Returns the cosys url.
     * 
     * <p><b>200</b> - The cosys url.
     * <p><b>500</b> - An unexpected system error occured.
     * @param body The body parameter
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec createCoSysUrlRequestCreation(CosysUrlParameter body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling createCoSysUrl", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI("/coSysUrl", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Returns the cosys url.
     * 
     * <p><b>200</b> - The cosys url.
     * <p><b>500</b> - An unexpected system error occured.
     * @param body The body parameter
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<String> createCoSysUrl(CosysUrlParameter body) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return createCoSysUrlRequestCreation(body).bodyToMono(localVarReturnType);
    }

    /**
     * Returns the cosys url.
     * 
     * <p><b>200</b> - The cosys url.
     * <p><b>500</b> - An unexpected system error occured.
     * @param body The body parameter
     * @return ResponseEntity&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<String>> createCoSysUrlWithHttpInfo(CosysUrlParameter body) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return createCoSysUrlRequestCreation(body).toEntity(localVarReturnType);
    }

    /**
     * Returns the cosys url.
     * 
     * <p><b>200</b> - The cosys url.
     * <p><b>500</b> - An unexpected system error occured.
     * @param body The body parameter
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec createCoSysUrlWithResponseSpec(CosysUrlParameter body) throws WebClientResponseException {
        return createCoSysUrlRequestCreation(body);
    }
    /**
     * Returns the cosys url.
     * 
     * <p><b>200</b> - The cosys url.
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template.
     * @param client The number of the client the template is located in.
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used.
     * @param templateName The name of the template.
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @param templateSearchparameter The template search parameter.   *Example:* &#x60;{ \&quot;cib_keyWords\&quot;: \&quot;test\&quot; }&#x60;
     * @param datapoolid The data pool id.
     * @param hideTree Hides the template tree.
     * @param hideTemplateSearch Hides the template search.
     * @param hideDraftTab Hides the draft tab.
     * @param lockDatacontext Locks the data context.
     * @param receiverName The receiver name.
     * @param receiverMail The receiver mail.
     * @param receiverOrg The receiver org.
     * @param firstTabSelector The tab selector.
     * @param firstTab The first tab. 0 - information of the template, 1 - next visible tab after information, 2 - receiver, 3 - contact person, 4 - signer, 5 - rest data acquisition, 6 - document creation, 7 - data supply small serial letter, 8 - data supply serial letter, 9 - partner
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getCoSysUrlRequestCreation(String guid, String client, String role, String templateName, String templateId, String templateType, String templateSearchparameter, String datapoolid, String hideTree, String hideTemplateSearch, String hideDraftTab, String lockDatacontext, String receiverName, String receiverMail, String receiverOrg, String firstTabSelector, Integer firstTab) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "guid", guid));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "client", client));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "role", role));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateName", templateName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateId", templateId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateType", templateType));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateSearchparameter", templateSearchparameter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "datapoolid", datapoolid));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "hideTree", hideTree));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "hideTemplateSearch", hideTemplateSearch));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "hideDraftTab", hideDraftTab));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "lockDatacontext", lockDatacontext));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "receiverName", receiverName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "receiverMail", receiverMail));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "receiverOrg", receiverOrg));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "firstTabSelector", firstTabSelector));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "firstTab", firstTab));

        final String[] localVarAccepts = { 
            "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI("/coSysUrl", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Returns the cosys url.
     * 
     * <p><b>200</b> - The cosys url.
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template.
     * @param client The number of the client the template is located in.
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used.
     * @param templateName The name of the template.
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @param templateSearchparameter The template search parameter.   *Example:* &#x60;{ \&quot;cib_keyWords\&quot;: \&quot;test\&quot; }&#x60;
     * @param datapoolid The data pool id.
     * @param hideTree Hides the template tree.
     * @param hideTemplateSearch Hides the template search.
     * @param hideDraftTab Hides the draft tab.
     * @param lockDatacontext Locks the data context.
     * @param receiverName The receiver name.
     * @param receiverMail The receiver mail.
     * @param receiverOrg The receiver org.
     * @param firstTabSelector The tab selector.
     * @param firstTab The first tab. 0 - information of the template, 1 - next visible tab after information, 2 - receiver, 3 - contact person, 4 - signer, 5 - rest data acquisition, 6 - document creation, 7 - data supply small serial letter, 8 - data supply serial letter, 9 - partner
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<String> getCoSysUrl(String guid, String client, String role, String templateName, String templateId, String templateType, String templateSearchparameter, String datapoolid, String hideTree, String hideTemplateSearch, String hideDraftTab, String lockDatacontext, String receiverName, String receiverMail, String receiverOrg, String firstTabSelector, Integer firstTab) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return getCoSysUrlRequestCreation(guid, client, role, templateName, templateId, templateType, templateSearchparameter, datapoolid, hideTree, hideTemplateSearch, hideDraftTab, lockDatacontext, receiverName, receiverMail, receiverOrg, firstTabSelector, firstTab).bodyToMono(localVarReturnType);
    }

    /**
     * Returns the cosys url.
     * 
     * <p><b>200</b> - The cosys url.
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template.
     * @param client The number of the client the template is located in.
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used.
     * @param templateName The name of the template.
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @param templateSearchparameter The template search parameter.   *Example:* &#x60;{ \&quot;cib_keyWords\&quot;: \&quot;test\&quot; }&#x60;
     * @param datapoolid The data pool id.
     * @param hideTree Hides the template tree.
     * @param hideTemplateSearch Hides the template search.
     * @param hideDraftTab Hides the draft tab.
     * @param lockDatacontext Locks the data context.
     * @param receiverName The receiver name.
     * @param receiverMail The receiver mail.
     * @param receiverOrg The receiver org.
     * @param firstTabSelector The tab selector.
     * @param firstTab The first tab. 0 - information of the template, 1 - next visible tab after information, 2 - receiver, 3 - contact person, 4 - signer, 5 - rest data acquisition, 6 - document creation, 7 - data supply small serial letter, 8 - data supply serial letter, 9 - partner
     * @return ResponseEntity&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<String>> getCoSysUrlWithHttpInfo(String guid, String client, String role, String templateName, String templateId, String templateType, String templateSearchparameter, String datapoolid, String hideTree, String hideTemplateSearch, String hideDraftTab, String lockDatacontext, String receiverName, String receiverMail, String receiverOrg, String firstTabSelector, Integer firstTab) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return getCoSysUrlRequestCreation(guid, client, role, templateName, templateId, templateType, templateSearchparameter, datapoolid, hideTree, hideTemplateSearch, hideDraftTab, lockDatacontext, receiverName, receiverMail, receiverOrg, firstTabSelector, firstTab).toEntity(localVarReturnType);
    }

    /**
     * Returns the cosys url.
     * 
     * <p><b>200</b> - The cosys url.
     * <p><b>500</b> - An unexpected system error occured.
     * @param guid The guid of the template.
     * @param client The number of the client the template is located in.
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used.
     * @param templateName The name of the template.
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @param templateSearchparameter The template search parameter.   *Example:* &#x60;{ \&quot;cib_keyWords\&quot;: \&quot;test\&quot; }&#x60;
     * @param datapoolid The data pool id.
     * @param hideTree Hides the template tree.
     * @param hideTemplateSearch Hides the template search.
     * @param hideDraftTab Hides the draft tab.
     * @param lockDatacontext Locks the data context.
     * @param receiverName The receiver name.
     * @param receiverMail The receiver mail.
     * @param receiverOrg The receiver org.
     * @param firstTabSelector The tab selector.
     * @param firstTab The first tab. 0 - information of the template, 1 - next visible tab after information, 2 - receiver, 3 - contact person, 4 - signer, 5 - rest data acquisition, 6 - document creation, 7 - data supply small serial letter, 8 - data supply serial letter, 9 - partner
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getCoSysUrlWithResponseSpec(String guid, String client, String role, String templateName, String templateId, String templateType, String templateSearchparameter, String datapoolid, String hideTree, String hideTemplateSearch, String hideDraftTab, String lockDatacontext, String receiverName, String receiverMail, String receiverOrg, String firstTabSelector, Integer firstTab) throws WebClientResponseException {
        return getCoSysUrlRequestCreation(guid, client, role, templateName, templateId, templateType, templateSearchparameter, datapoolid, hideTree, hideTemplateSearch, hideDraftTab, lockDatacontext, receiverName, receiverMail, receiverOrg, firstTabSelector, firstTab);
    }
    /**
     * Returns the GUID for the template identifier.
     * 
     * <p><b>200</b> - The GUID for the template identifier.
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Template not found.
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec getGuidByTemplateIdRequestCreation(String client, String role, String templateId, String templateType) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling getGuidByTemplateId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling getGuidByTemplateId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'templateId' is set
        if (templateId == null) {
            throw new WebClientResponseException("Missing the required parameter 'templateId' when calling getGuidByTemplateId", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "client", client));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "role", role));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateId", templateId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateType", templateType));

        final String[] localVarAccepts = { 
            "text/plain"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return apiClient.invokeAPI("/guidByTemplateId", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Returns the GUID for the template identifier.
     * 
     * <p><b>200</b> - The GUID for the template identifier.
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Template not found.
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @return String
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<String> getGuidByTemplateId(String client, String role, String templateId, String templateType) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return getGuidByTemplateIdRequestCreation(client, role, templateId, templateType).bodyToMono(localVarReturnType);
    }

    /**
     * Returns the GUID for the template identifier.
     * 
     * <p><b>200</b> - The GUID for the template identifier.
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Template not found.
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @return ResponseEntity&lt;String&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<String>> getGuidByTemplateIdWithHttpInfo(String client, String role, String templateId, String templateType) throws WebClientResponseException {
        ParameterizedTypeReference<String> localVarReturnType = new ParameterizedTypeReference<String>() {};
        return getGuidByTemplateIdRequestCreation(client, role, templateId, templateType).toEntity(localVarReturnType);
    }

    /**
     * Returns the GUID for the template identifier.
     * 
     * <p><b>200</b> - The GUID for the template identifier.
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - Template not found.
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec getGuidByTemplateIdWithResponseSpec(String client, String role, String templateId, String templateType) throws WebClientResponseException {
        return getGuidByTemplateIdRequestCreation(client, role, templateId, templateType);
    }
    /**
     * Returns a list of template information for the given search parameters.
     * 
     * <p><b>200</b> - The list of template information.
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param templateName The name of the template.
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @return List&lt;SearchResult&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec searchTemplatesRequestCreation(String client, String role, String templateName, String templateId, String templateType) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling searchTemplates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling searchTemplates", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "client", client));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "role", role));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateName", templateName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateId", templateId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateType", templateType));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<SearchResult> localVarReturnType = new ParameterizedTypeReference<SearchResult>() {};
        return apiClient.invokeAPI("/searchTemplates", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Returns a list of template information for the given search parameters.
     * 
     * <p><b>200</b> - The list of template information.
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param templateName The name of the template.
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @return List&lt;SearchResult&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<SearchResult> searchTemplates(String client, String role, String templateName, String templateId, String templateType) throws WebClientResponseException {
        ParameterizedTypeReference<SearchResult> localVarReturnType = new ParameterizedTypeReference<SearchResult>() {};
        return searchTemplatesRequestCreation(client, role, templateName, templateId, templateType).bodyToFlux(localVarReturnType);
    }

    /**
     * Returns a list of template information for the given search parameters.
     * 
     * <p><b>200</b> - The list of template information.
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param templateName The name of the template.
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @return ResponseEntity&lt;List&lt;SearchResult&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<SearchResult>>> searchTemplatesWithHttpInfo(String client, String role, String templateName, String templateId, String templateType) throws WebClientResponseException {
        ParameterizedTypeReference<SearchResult> localVarReturnType = new ParameterizedTypeReference<SearchResult>() {};
        return searchTemplatesRequestCreation(client, role, templateName, templateId, templateType).toEntityList(localVarReturnType);
    }

    /**
     * Returns a list of template information for the given search parameters.
     * 
     * <p><b>200</b> - The list of template information.
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>500</b> - An unexpected system error occured.
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param templateName The name of the template.
     * @param templateId The identifier (form number) of the template.
     * @param templateType The type of the template.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec searchTemplatesWithResponseSpec(String client, String role, String templateName, String templateId, String templateType) throws WebClientResponseException {
        return searchTemplatesRequestCreation(client, role, templateName, templateId, templateType);
    }
}
