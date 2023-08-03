package de.muenchen.oss.digiwf.cosys.integration.gen.api;

import de.muenchen.oss.digiwf.cosys.integration.gen.ApiClient;

import java.io.File;
import de.muenchen.oss.digiwf.cosys.integration.gen.model.ReadPdfFieldsOutput;

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
public class PdfApi {
    private ApiClient apiClient;

    public PdfApi() {
        this(new ApiClient());
    }

    @Autowired
    public PdfApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Concat multiple PDF files
     * 
     * <p><b>200</b> - Joined PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file Multiple PDF files which will be joined to one PDF.
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec concatPdfRequestCreation(List<File> _file) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new WebClientResponseException("Missing the required parameter '_file' when calling concatPdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (_file != null)
            formParams.addAll("file", _file.stream().map(FileSystemResource::new).collect(Collectors.toList()));

        final String[] localVarAccepts = { 
            "application/pdf"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return apiClient.invokeAPI("/pdf/concatPdf", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Concat multiple PDF files
     * 
     * <p><b>200</b> - Joined PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file Multiple PDF files which will be joined to one PDF.
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<byte[]> concatPdf(List<File> _file) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return concatPdfRequestCreation(_file).bodyToMono(localVarReturnType);
    }

    /**
     * Concat multiple PDF files
     * 
     * <p><b>200</b> - Joined PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file Multiple PDF files which will be joined to one PDF.
     * @return ResponseEntity&lt;byte[]&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<byte[]>> concatPdfWithHttpInfo(List<File> _file) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return concatPdfRequestCreation(_file).toEntity(localVarReturnType);
    }

    /**
     * Concat multiple PDF files
     * 
     * <p><b>200</b> - Joined PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file Multiple PDF files which will be joined to one PDF.
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec concatPdfWithResponseSpec(List<File> _file) throws WebClientResponseException {
        return concatPdfRequestCreation(_file);
    }
    /**
     * Convert a PDF to a PDF/A-3b
     * 
     * <p><b>200</b> - PDF/A-3b file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file which will be converted to a PDF/A-3b file
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec convertPdfToPdfaRequestCreation(File _file) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new WebClientResponseException("Missing the required parameter '_file' when calling convertPdfToPdfa", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (_file != null)
            formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { 
            "application/pdf"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return apiClient.invokeAPI("/pdf/convertPdfToPdfa", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Convert a PDF to a PDF/A-3b
     * 
     * <p><b>200</b> - PDF/A-3b file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file which will be converted to a PDF/A-3b file
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<byte[]> convertPdfToPdfa(File _file) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return convertPdfToPdfaRequestCreation(_file).bodyToMono(localVarReturnType);
    }

    /**
     * Convert a PDF to a PDF/A-3b
     * 
     * <p><b>200</b> - PDF/A-3b file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file which will be converted to a PDF/A-3b file
     * @return ResponseEntity&lt;byte[]&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<byte[]>> convertPdfToPdfaWithHttpInfo(File _file) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return convertPdfToPdfaRequestCreation(_file).toEntity(localVarReturnType);
    }

    /**
     * Convert a PDF to a PDF/A-3b
     * 
     * <p><b>200</b> - PDF/A-3b file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file which will be converted to a PDF/A-3b file
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec convertPdfToPdfaWithResponseSpec(File _file) throws WebClientResponseException {
        return convertPdfToPdfaRequestCreation(_file);
    }
    /**
     * Convert a file to a PDF file
     * 
     * <p><b>200</b> - Created PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file Input file which will be converted to a PDF file. Supported formats: RTF(text/rtf), PDF, Images (jpeg, png, git, tiff), txt-Format Currently not supported: all ODF (odt, ott, docx ...)
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec convertToPdfRequestCreation(File _file) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new WebClientResponseException("Missing the required parameter '_file' when calling convertToPdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (_file != null)
            formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { 
            "application/pdf"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return apiClient.invokeAPI("/pdf/convertToPdf", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Convert a file to a PDF file
     * 
     * <p><b>200</b> - Created PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file Input file which will be converted to a PDF file. Supported formats: RTF(text/rtf), PDF, Images (jpeg, png, git, tiff), txt-Format Currently not supported: all ODF (odt, ott, docx ...)
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<byte[]> convertToPdf(File _file) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return convertToPdfRequestCreation(_file).bodyToMono(localVarReturnType);
    }

    /**
     * Convert a file to a PDF file
     * 
     * <p><b>200</b> - Created PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file Input file which will be converted to a PDF file. Supported formats: RTF(text/rtf), PDF, Images (jpeg, png, git, tiff), txt-Format Currently not supported: all ODF (odt, ott, docx ...)
     * @return ResponseEntity&lt;byte[]&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<byte[]>> convertToPdfWithHttpInfo(File _file) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return convertToPdfRequestCreation(_file).toEntity(localVarReturnType);
    }

    /**
     * Convert a file to a PDF file
     * 
     * <p><b>200</b> - Created PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file Input file which will be converted to a PDF file. Supported formats: RTF(text/rtf), PDF, Images (jpeg, png, git, tiff), txt-Format Currently not supported: all ODF (odt, ott, docx ...)
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec convertToPdfWithResponseSpec(File _file) throws WebClientResponseException {
        return convertToPdfRequestCreation(_file);
    }
    /**
     * Encrypt a PDF file with a password
     * 
     * <p><b>200</b> - Encrypted PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param password Password that will be used to encrypt the PDF file
     * @param _file PDF file which will be encrypted
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec encryptPdfRequestCreation(String password, File _file) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'password' is set
        if (password == null) {
            throw new WebClientResponseException("Missing the required parameter 'password' when calling encryptPdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new WebClientResponseException("Missing the required parameter '_file' when calling encryptPdf", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "password", password));

        if (_file != null)
            formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { 
            "application/pdf"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return apiClient.invokeAPI("/pdf/encryptPdf", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Encrypt a PDF file with a password
     * 
     * <p><b>200</b> - Encrypted PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param password Password that will be used to encrypt the PDF file
     * @param _file PDF file which will be encrypted
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<byte[]> encryptPdf(String password, File _file) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return encryptPdfRequestCreation(password, _file).bodyToMono(localVarReturnType);
    }

    /**
     * Encrypt a PDF file with a password
     * 
     * <p><b>200</b> - Encrypted PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param password Password that will be used to encrypt the PDF file
     * @param _file PDF file which will be encrypted
     * @return ResponseEntity&lt;byte[]&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<byte[]>> encryptPdfWithHttpInfo(String password, File _file) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return encryptPdfRequestCreation(password, _file).toEntity(localVarReturnType);
    }

    /**
     * Encrypt a PDF file with a password
     * 
     * <p><b>200</b> - Encrypted PDF file
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param password Password that will be used to encrypt the PDF file
     * @param _file PDF file which will be encrypted
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec encryptPdfWithResponseSpec(String password, File _file) throws WebClientResponseException {
        return encryptPdfRequestCreation(password, _file);
    }
    /**
     * Fill values into the fields of a PDF
     * 
     * <p><b>200</b> - PDF with values in fields
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file into which the values will be written
     * @param data Values as key value map as JSON object
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec fillPdfFieldsRequestCreation(File _file, File data) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new WebClientResponseException("Missing the required parameter '_file' when calling fillPdfFields", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'data' is set
        if (data == null) {
            throw new WebClientResponseException("Missing the required parameter 'data' when calling fillPdfFields", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (_file != null)
            formParams.add("file", new FileSystemResource(_file));
        if (data != null)
            formParams.add("data", new FileSystemResource(data));

        final String[] localVarAccepts = { 
            "application/pdf"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return apiClient.invokeAPI("/pdf/fillPdfFields", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Fill values into the fields of a PDF
     * 
     * <p><b>200</b> - PDF with values in fields
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file into which the values will be written
     * @param data Values as key value map as JSON object
     * @return byte[]
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<byte[]> fillPdfFields(File _file, File data) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return fillPdfFieldsRequestCreation(_file, data).bodyToMono(localVarReturnType);
    }

    /**
     * Fill values into the fields of a PDF
     * 
     * <p><b>200</b> - PDF with values in fields
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file into which the values will be written
     * @param data Values as key value map as JSON object
     * @return ResponseEntity&lt;byte[]&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<byte[]>> fillPdfFieldsWithHttpInfo(File _file, File data) throws WebClientResponseException {
        ParameterizedTypeReference<byte[]> localVarReturnType = new ParameterizedTypeReference<byte[]>() {};
        return fillPdfFieldsRequestCreation(_file, data).toEntity(localVarReturnType);
    }

    /**
     * Fill values into the fields of a PDF
     * 
     * <p><b>200</b> - PDF with values in fields
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file into which the values will be written
     * @param data Values as key value map as JSON object
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec fillPdfFieldsWithResponseSpec(File _file, File data) throws WebClientResponseException {
        return fillPdfFieldsRequestCreation(_file, data);
    }
    /**
     * Read the fields of a PDF
     * 
     * <p><b>200</b> - field values in XML format
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file from which the field values will be read
     * @return ReadPdfFieldsOutput
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec readPdfFieldsRequestCreation(File _file) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new WebClientResponseException("Missing the required parameter '_file' when calling readPdfFields", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (_file != null)
            formParams.add("file", new FileSystemResource(_file));

        final String[] localVarAccepts = { 
            "application/xml"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<ReadPdfFieldsOutput> localVarReturnType = new ParameterizedTypeReference<ReadPdfFieldsOutput>() {};
        return apiClient.invokeAPI("/pdf/readPdfFields", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Read the fields of a PDF
     * 
     * <p><b>200</b> - field values in XML format
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file from which the field values will be read
     * @return ReadPdfFieldsOutput
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ReadPdfFieldsOutput> readPdfFields(File _file) throws WebClientResponseException {
        ParameterizedTypeReference<ReadPdfFieldsOutput> localVarReturnType = new ParameterizedTypeReference<ReadPdfFieldsOutput>() {};
        return readPdfFieldsRequestCreation(_file).bodyToMono(localVarReturnType);
    }

    /**
     * Read the fields of a PDF
     * 
     * <p><b>200</b> - field values in XML format
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file from which the field values will be read
     * @return ResponseEntity&lt;ReadPdfFieldsOutput&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<ReadPdfFieldsOutput>> readPdfFieldsWithHttpInfo(File _file) throws WebClientResponseException {
        ParameterizedTypeReference<ReadPdfFieldsOutput> localVarReturnType = new ParameterizedTypeReference<ReadPdfFieldsOutput>() {};
        return readPdfFieldsRequestCreation(_file).toEntity(localVarReturnType);
    }

    /**
     * Read the fields of a PDF
     * 
     * <p><b>200</b> - field values in XML format
     * <p><b>415</b> - Input not supported
     * <p><b>404</b> - coSys instance could not be reached
     * <p><b>500</b> - An unexpected system error occured.
     * @param _file PDF file from which the field values will be read
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec readPdfFieldsWithResponseSpec(File _file) throws WebClientResponseException {
        return readPdfFieldsRequestCreation(_file);
    }
}
