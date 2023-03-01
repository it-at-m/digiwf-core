package io.muenchendigital.digiwf.cosys.integration.gen.api;

import io.muenchendigital.digiwf.cosys.integration.gen.ApiClient;

import java.io.File;

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
public class GenerationApi {
    private ApiClient apiClient;

    public GenerationApi() {
        this(new ApiClient());
    }

    @Autowired
    public GenerationApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Creates a PDF document from a template
     * 
     * <p><b>200</b> - Created PDF file
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>412</b> - Precondition failed. Used fields in template and provided data don&#39;t match. Response body contains information about the error.
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data that is merged into the template. Allowed mime types are: application/xml, application/json and text/csv
     * @param outputFormat The output format of the PDF.
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param dataProviderInput Input for the data provider.  *Example:* &#x60;&#x60;&#x60; {   \&quot;name\&quot; : \&quot;Fridolin Fröhlich\&quot; } &#x60;&#x60;&#x60;
     * @param userId User id (lhmObjectId) of the selected sender as input for data provider. If the input for the provider is set and the userId isn&#39;t set, then the user id from login is used.
     * @param noStatistic Disable statistics. This parameter should only be used for testing. The default is false.
     * @param merge The parameter for the merge module as application/json. If no parameter file is provided the default value is used.  *Default value:* &#x60;&#x60;&#x60; {      \\\&quot;--prefix-delimiter\\\&quot;:\\\&quot;.\\\&quot;,      \\\&quot;--datafile\\\&quot;:\\\&quot;//multi\\\&quot;,      \\\&quot;--input-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,      \\\&quot;--output-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,      \\\&quot;-@1\\\&quot;:\\\&quot;\\\&quot;,      \\\&quot;--logfile\\\&quot;:\\\&quot;merge.log\\\&quot;  }   &#x60;&#x60;&#x60;
     * @param resource Additional files (e.g. images) included from the template
     * @param format The parameter for the format module as application/json. This controls the PDF generation. If not set, the default parameters for the output format will be used.  *Example:* &#x60;&#x60;&#x60; {      \\\&quot;OutputFormat\\\&quot;: \\\&quot;FormatPdfA\\\&quot;,      \\\&quot;PdfVersion\\\&quot;: \\\&quot;PDF/A-3b\\\&quot;  } &#x60;&#x60;&#x60;
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec generatePdfRequestCreation(String guid, String client, String role, File data, String outputFormat, String stateFilter, String validity, Object dataProviderInput, String userId, Boolean noStatistic, File merge, List<File> resource, File format) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'guid' is set
        if (guid == null) {
            throw new WebClientResponseException("Missing the required parameter 'guid' when calling generatePdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling generatePdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling generatePdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'data' is set
        if (data == null) {
            throw new WebClientResponseException("Missing the required parameter 'data' when calling generatePdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "outputFormat", outputFormat));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stateFilter", stateFilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validity", validity));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataProviderInput", dataProviderInput));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userId", userId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "noStatistic", noStatistic));

        if (data != null)
            formParams.add("data", new FileSystemResource(data));
        if (merge != null)
            formParams.add("merge", new FileSystemResource(merge));
        if (resource != null)
            formParams.addAll("resource", resource.stream().map(FileSystemResource::new).collect(Collectors.toList()));
        if (format != null)
            formParams.add("format", new FileSystemResource(format));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return apiClient.invokeAPI("/generation/{guid}/pdf", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a PDF document from a template
     * 
     * <p><b>200</b> - Created PDF file
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>412</b> - Precondition failed. Used fields in template and provided data don&#39;t match. Response body contains information about the error.
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data that is merged into the template. Allowed mime types are: application/xml, application/json and text/csv
     * @param outputFormat The output format of the PDF.
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param dataProviderInput Input for the data provider.  *Example:* &#x60;&#x60;&#x60; {   \&quot;name\&quot; : \&quot;Fridolin Fröhlich\&quot; } &#x60;&#x60;&#x60;
     * @param userId User id (lhmObjectId) of the selected sender as input for data provider. If the input for the provider is set and the userId isn&#39;t set, then the user id from login is used.
     * @param noStatistic Disable statistics. This parameter should only be used for testing. The default is false.
     * @param merge The parameter for the merge module as application/json. If no parameter file is provided the default value is used.  *Default value:* &#x60;&#x60;&#x60; {      \\\&quot;--prefix-delimiter\\\&quot;:\\\&quot;.\\\&quot;,      \\\&quot;--datafile\\\&quot;:\\\&quot;//multi\\\&quot;,      \\\&quot;--input-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,      \\\&quot;--output-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,      \\\&quot;-@1\\\&quot;:\\\&quot;\\\&quot;,      \\\&quot;--logfile\\\&quot;:\\\&quot;merge.log\\\&quot;  }   &#x60;&#x60;&#x60;
     * @param resource Additional files (e.g. images) included from the template
     * @param format The parameter for the format module as application/json. This controls the PDF generation. If not set, the default parameters for the output format will be used.  *Example:* &#x60;&#x60;&#x60; {      \\\&quot;OutputFormat\\\&quot;: \\\&quot;FormatPdfA\\\&quot;,      \\\&quot;PdfVersion\\\&quot;: \\\&quot;PDF/A-3b\\\&quot;  } &#x60;&#x60;&#x60;
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<byte[]> generatePdf(String guid, String client, String role, File data, String outputFormat, String stateFilter, String validity, Object dataProviderInput, String userId, Boolean noStatistic, File merge, List<File> resource, File format) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return generatePdfRequestCreation(guid, client, role, data, outputFormat, stateFilter, validity, dataProviderInput, userId, noStatistic, merge, resource, format).bodyToMono(localVarReturnType);
    }

    /**
     * Creates a PDF document from a template
     * 
     * <p><b>200</b> - Created PDF file
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>412</b> - Precondition failed. Used fields in template and provided data don&#39;t match. Response body contains information about the error.
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data that is merged into the template. Allowed mime types are: application/xml, application/json and text/csv
     * @param outputFormat The output format of the PDF.
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param dataProviderInput Input for the data provider.  *Example:* &#x60;&#x60;&#x60; {   \&quot;name\&quot; : \&quot;Fridolin Fröhlich\&quot; } &#x60;&#x60;&#x60;
     * @param userId User id (lhmObjectId) of the selected sender as input for data provider. If the input for the provider is set and the userId isn&#39;t set, then the user id from login is used.
     * @param noStatistic Disable statistics. This parameter should only be used for testing. The default is false.
     * @param merge The parameter for the merge module as application/json. If no parameter file is provided the default value is used.  *Default value:* &#x60;&#x60;&#x60; {      \\\&quot;--prefix-delimiter\\\&quot;:\\\&quot;.\\\&quot;,      \\\&quot;--datafile\\\&quot;:\\\&quot;//multi\\\&quot;,      \\\&quot;--input-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,      \\\&quot;--output-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,      \\\&quot;-@1\\\&quot;:\\\&quot;\\\&quot;,      \\\&quot;--logfile\\\&quot;:\\\&quot;merge.log\\\&quot;  }   &#x60;&#x60;&#x60;
     * @param resource Additional files (e.g. images) included from the template
     * @param format The parameter for the format module as application/json. This controls the PDF generation. If not set, the default parameters for the output format will be used.  *Example:* &#x60;&#x60;&#x60; {      \\\&quot;OutputFormat\\\&quot;: \\\&quot;FormatPdfA\\\&quot;,      \\\&quot;PdfVersion\\\&quot;: \\\&quot;PDF/A-3b\\\&quot;  } &#x60;&#x60;&#x60;
     * @return ResponseEntity&lt;byte[]&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<byte[]>> generatePdfWithHttpInfo(String guid, String client, String role, File data, String outputFormat, String stateFilter, String validity, Object dataProviderInput, String userId, Boolean noStatistic, File merge, List<File> resource, File format) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return generatePdfRequestCreation(guid, client, role, data, outputFormat, stateFilter, validity, dataProviderInput, userId, noStatistic, merge, resource, format).toEntity(localVarReturnType);
    }

    /**
     * Creates a PDF document from a template
     * 
     * <p><b>200</b> - Created PDF file
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>412</b> - Precondition failed. Used fields in template and provided data don&#39;t match. Response body contains information about the error.
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data that is merged into the template. Allowed mime types are: application/xml, application/json and text/csv
     * @param outputFormat The output format of the PDF.
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param dataProviderInput Input for the data provider.  *Example:* &#x60;&#x60;&#x60; {   \&quot;name\&quot; : \&quot;Fridolin Fröhlich\&quot; } &#x60;&#x60;&#x60;
     * @param userId User id (lhmObjectId) of the selected sender as input for data provider. If the input for the provider is set and the userId isn&#39;t set, then the user id from login is used.
     * @param noStatistic Disable statistics. This parameter should only be used for testing. The default is false.
     * @param merge The parameter for the merge module as application/json. If no parameter file is provided the default value is used.  *Default value:* &#x60;&#x60;&#x60; {      \\\&quot;--prefix-delimiter\\\&quot;:\\\&quot;.\\\&quot;,      \\\&quot;--datafile\\\&quot;:\\\&quot;//multi\\\&quot;,      \\\&quot;--input-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,      \\\&quot;--output-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,      \\\&quot;-@1\\\&quot;:\\\&quot;\\\&quot;,      \\\&quot;--logfile\\\&quot;:\\\&quot;merge.log\\\&quot;  }   &#x60;&#x60;&#x60;
     * @param resource Additional files (e.g. images) included from the template
     * @param format The parameter for the format module as application/json. This controls the PDF generation. If not set, the default parameters for the output format will be used.  *Example:* &#x60;&#x60;&#x60; {      \\\&quot;OutputFormat\\\&quot;: \\\&quot;FormatPdfA\\\&quot;,      \\\&quot;PdfVersion\\\&quot;: \\\&quot;PDF/A-3b\\\&quot;  } &#x60;&#x60;&#x60;
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec generatePdfWithResponseSpec(String guid, String client, String role, File data, String outputFormat, String stateFilter, String validity, Object dataProviderInput, String userId, Boolean noStatistic, File merge, List<File> resource, File format) throws WebClientResponseException {
        return generatePdfRequestCreation(guid, client, role, data, outputFormat, stateFilter, validity, dataProviderInput, userId, noStatistic, merge, resource, format);
    }
    /**
     * Creates a RTF document from a template
     * 
     * <p><b>200</b> - Created RTF file
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>412</b> - Precondition failed. Used fields in template and provided data don&#39;t match. Response body contains information about the error.
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data that is merged into the template. Allowed mime types are: application/xml, application/json and text/csv
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param dataProviderInput Input for the data provider.  *Example:* &#x60;&#x60;&#x60; {   \&quot;name\&quot; : \&quot;Fridolin Fröhlich\&quot; } &#x60;&#x60;&#x60;
     * @param userId User id (lhmObjectId) of the selected sender as input for data provider. If the input for the provider is set and the userId isn&#39;t set, then the user id from login is used.
     * @param noStatistic Disable statistics. This parameter should only be used for testing. The default is false.
     * @param merge The parameter for the merge module as application/json. If no parameter file is provided the default value is used.  *Default value:* &#x60;&#x60;&#x60; {     \\\&quot;--prefix-delimiter\\\&quot;:\\\&quot;.\\\&quot;,     \\\&quot;--datafile\\\&quot;:\\\&quot;//multi\\\&quot;,     \\\&quot;--input-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,     \\\&quot;--output-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,     \\\&quot;-@1\\\&quot;:\\\&quot;\\\&quot;,     \\\&quot;--logfile\\\&quot;:\\\&quot;merge.log\\\&quot;,     \\\&quot;--remove-hidden-text\\\&quot;:\\\&quot;\\\&quot; }  &#x60;&#x60;&#x60;
     * @param resource Additional files (e.g. images) included from the template
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec generateRtfRequestCreation(String guid, String client, String role, File data, String stateFilter, String validity, Object dataProviderInput, String userId, Boolean noStatistic, File merge, List<File> resource) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'guid' is set
        if (guid == null) {
            throw new WebClientResponseException("Missing the required parameter 'guid' when calling generateRtf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'client' is set
        if (client == null) {
            throw new WebClientResponseException("Missing the required parameter 'client' when calling generateRtf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new WebClientResponseException("Missing the required parameter 'role' when calling generateRtf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'data' is set
        if (data == null) {
            throw new WebClientResponseException("Missing the required parameter 'data' when calling generateRtf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "stateFilter", stateFilter));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "validity", validity));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "dataProviderInput", dataProviderInput));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "userId", userId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "noStatistic", noStatistic));

        if (data != null)
            formParams.add("data", new FileSystemResource(data));
        if (merge != null)
            formParams.add("merge", new FileSystemResource(merge));
        if (resource != null)
            formParams.addAll("resource", resource.stream().map(FileSystemResource::new).collect(Collectors.toList()));

        final String[] localVarAccepts = { 
            "application/octet-stream"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuthSecurityDefintion", "accessTokenSecurityDefintion" };

        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return apiClient.invokeAPI("/generation/{guid}/rtf", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Creates a RTF document from a template
     * 
     * <p><b>200</b> - Created RTF file
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>412</b> - Precondition failed. Used fields in template and provided data don&#39;t match. Response body contains information about the error.
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data that is merged into the template. Allowed mime types are: application/xml, application/json and text/csv
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param dataProviderInput Input for the data provider.  *Example:* &#x60;&#x60;&#x60; {   \&quot;name\&quot; : \&quot;Fridolin Fröhlich\&quot; } &#x60;&#x60;&#x60;
     * @param userId User id (lhmObjectId) of the selected sender as input for data provider. If the input for the provider is set and the userId isn&#39;t set, then the user id from login is used.
     * @param noStatistic Disable statistics. This parameter should only be used for testing. The default is false.
     * @param merge The parameter for the merge module as application/json. If no parameter file is provided the default value is used.  *Default value:* &#x60;&#x60;&#x60; {     \\\&quot;--prefix-delimiter\\\&quot;:\\\&quot;.\\\&quot;,     \\\&quot;--datafile\\\&quot;:\\\&quot;//multi\\\&quot;,     \\\&quot;--input-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,     \\\&quot;--output-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,     \\\&quot;-@1\\\&quot;:\\\&quot;\\\&quot;,     \\\&quot;--logfile\\\&quot;:\\\&quot;merge.log\\\&quot;,     \\\&quot;--remove-hidden-text\\\&quot;:\\\&quot;\\\&quot; }  &#x60;&#x60;&#x60;
     * @param resource Additional files (e.g. images) included from the template
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<byte[]> generateRtf(String guid, String client, String role, File data, String stateFilter, String validity, Object dataProviderInput, String userId, Boolean noStatistic, File merge, List<File> resource) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return generateRtfRequestCreation(guid, client, role, data, stateFilter, validity, dataProviderInput, userId, noStatistic, merge, resource).bodyToMono(localVarReturnType);
    }

    /**
     * Creates a RTF document from a template
     * 
     * <p><b>200</b> - Created RTF file
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>412</b> - Precondition failed. Used fields in template and provided data don&#39;t match. Response body contains information about the error.
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data that is merged into the template. Allowed mime types are: application/xml, application/json and text/csv
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param dataProviderInput Input for the data provider.  *Example:* &#x60;&#x60;&#x60; {   \&quot;name\&quot; : \&quot;Fridolin Fröhlich\&quot; } &#x60;&#x60;&#x60;
     * @param userId User id (lhmObjectId) of the selected sender as input for data provider. If the input for the provider is set and the userId isn&#39;t set, then the user id from login is used.
     * @param noStatistic Disable statistics. This parameter should only be used for testing. The default is false.
     * @param merge The parameter for the merge module as application/json. If no parameter file is provided the default value is used.  *Default value:* &#x60;&#x60;&#x60; {     \\\&quot;--prefix-delimiter\\\&quot;:\\\&quot;.\\\&quot;,     \\\&quot;--datafile\\\&quot;:\\\&quot;//multi\\\&quot;,     \\\&quot;--input-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,     \\\&quot;--output-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,     \\\&quot;-@1\\\&quot;:\\\&quot;\\\&quot;,     \\\&quot;--logfile\\\&quot;:\\\&quot;merge.log\\\&quot;,     \\\&quot;--remove-hidden-text\\\&quot;:\\\&quot;\\\&quot; }  &#x60;&#x60;&#x60;
     * @param resource Additional files (e.g. images) included from the template
     * @return ResponseEntity&lt;byte[]&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<byte[]>> generateRtfWithHttpInfo(String guid, String client, String role, File data, String stateFilter, String validity, Object dataProviderInput, String userId, Boolean noStatistic, File merge, List<File> resource) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return generateRtfRequestCreation(guid, client, role, data, stateFilter, validity, dataProviderInput, userId, noStatistic, merge, resource).toEntity(localVarReturnType);
    }

    /**
     * Creates a RTF document from a template
     * 
     * <p><b>200</b> - Created RTF file
     * <p><b>403</b> - Client does not exist, Role TO is not allowed
     * <p><b>401</b> - Unauthorized
     * <p><b>404</b> - coSys instance could not be reached or the template (version) does not exist
     * <p><b>412</b> - Precondition failed. Used fields in template and provided data don&#39;t match. Response body contains information about the error.
     * <p><b>500</b> - An unexpected system error occured. Response body might contain ZIP file that can be used for further error analysis.
     * @param guid The guid of the template
     * @param client The number of the client the template is located in
     * @param role The role that should be used for the document generation. With the role \&quot;SB\&quot; only released template versions can be used
     * @param data The data that is merged into the template. Allowed mime types are: application/xml, application/json and text/csv
     * @param stateFilter Release state of the template version
     * @param validity Validity date to select a specific template version. Date must be in ISO 8601, e.g. 2020-04-09T13:49:55.000Z. Default value current date/time
     * @param dataProviderInput Input for the data provider.  *Example:* &#x60;&#x60;&#x60; {   \&quot;name\&quot; : \&quot;Fridolin Fröhlich\&quot; } &#x60;&#x60;&#x60;
     * @param userId User id (lhmObjectId) of the selected sender as input for data provider. If the input for the provider is set and the userId isn&#39;t set, then the user id from login is used.
     * @param noStatistic Disable statistics. This parameter should only be used for testing. The default is false.
     * @param merge The parameter for the merge module as application/json. If no parameter file is provided the default value is used.  *Default value:* &#x60;&#x60;&#x60; {     \\\&quot;--prefix-delimiter\\\&quot;:\\\&quot;.\\\&quot;,     \\\&quot;--datafile\\\&quot;:\\\&quot;//multi\\\&quot;,     \\\&quot;--input-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,     \\\&quot;--output-language\\\&quot;:\\\&quot;Deutsch\\\&quot;,     \\\&quot;-@1\\\&quot;:\\\&quot;\\\&quot;,     \\\&quot;--logfile\\\&quot;:\\\&quot;merge.log\\\&quot;,     \\\&quot;--remove-hidden-text\\\&quot;:\\\&quot;\\\&quot; }  &#x60;&#x60;&#x60;
     * @param resource Additional files (e.g. images) included from the template
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec generateRtfWithResponseSpec(String guid, String client, String role, File data, String stateFilter, String validity, Object dataProviderInput, String userId, Boolean noStatistic, File merge, List<File> resource) throws WebClientResponseException {
        return generateRtfRequestCreation(guid, client, role, data, stateFilter, validity, dataProviderInput, userId, noStatistic, merge, resource);
    }
}
