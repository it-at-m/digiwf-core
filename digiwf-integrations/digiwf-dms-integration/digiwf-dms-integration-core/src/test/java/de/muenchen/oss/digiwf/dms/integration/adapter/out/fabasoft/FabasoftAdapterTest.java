package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.*;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.muenchen.oss.digiwf.dms.integration.domain.*;
import de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.FabasoftClienFactory;
import de.muenchen.oss.digiwf.integration.e2e.test.wsdl.DigiwfWiremockWsdlUtility;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest()
class FabasoftAdapterTest {

    private final FabasoftProperties properties = new FabasoftProperties();
    private LHMBAI151700GIWSDSoap soapClient;
    private FabasoftAdapter fabasoftAdapter;


    @BeforeEach
    public void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        this.properties.setUsername("user");
        this.properties.setPassword("password");
        this.properties.setBusinessapp("businessapp");
        soapClient = FabasoftClienFactory.dmsWsClient("http://localhost:" + wmRuntimeInfo.getHttpPort() + "/");
        fabasoftAdapter = new FabasoftAdapter(properties, this.soapClient);
    }

    @Test
    void execute_createFile_request() {
        val response = new CreateFileGIResponse();
        response.setObjid("1234567890");

        DigiwfWiremockWsdlUtility.stubOperation(
                "CreateFileGI",
                CreateFileGI.class, (u) -> "new file".equals(u.getShortname()),
                response);

        val file = new File("apentryCOO", "new file");

        val procedureResponse = fabasoftAdapter.createFile(file, "user");

        assertEquals(procedureResponse, "1234567890");
    }

    @Test
    void execute_createProcedure_request() {
        val response = new CreateProcedureGIResponse();
        response.setObjid("1234567890");

        DigiwfWiremockWsdlUtility.stubOperation(
                "CreateProcedureGI",
                CreateProcedureGI.class, (u) -> "new procedure".equals(u.getShortname()),
                response);

        val procedure = new Procedure("fileCOO", "new procedure");

        val procedureResponse = fabasoftAdapter.createProcedure(procedure, "user");

        assertEquals(procedureResponse.getCoo(), "1234567890");
    }

    @Test
    void execute_depositObject_request() {
        val response = new DepositObjectGIResponse();
        response.setObjid("objectCoo");

        DigiwfWiremockWsdlUtility.stubOperation(
                "DepositObjectGI",
                DepositObjectGI.class, (u) -> true,
                response);


        fabasoftAdapter.depositObject("objectCoo", "user");
    }

    @Test
    void execute_createIncomingDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new CreateIncomingGIResponse();
        response.setObjid("documentCOO");

        DigiwfWiremockWsdlUtility.stubOperation(
                "CreateIncomingGI",
                CreateIncomingGI.class, (u) -> true,
                response);

        val documentResponse = fabasoftAdapter.createDocument(new Document("procedureCOO", "title", DocumentType.EINGEHEND, List.of(content)), "user");

        assertEquals(documentResponse, "documentCOO");
    }

    @Test
    void execute_createOutgoingDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new CreateOutgoingGIResponse();
        response.setObjid("documentCOO");

        DigiwfWiremockWsdlUtility.stubOperation(
                "CreateOutgoingGI",
                CreateOutgoingGI.class, (u) -> true,
                response);

        val documentResponse = fabasoftAdapter.createDocument(new Document("procedureCOO", "title", DocumentType.AUSGEHEND, List.of(content)), "user");

        assertEquals(documentResponse, "documentCOO");
    }

    @Test
    void execute_createInternalDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new CreateInternalGIResponse();
        response.setObjid("documentCOO");

        DigiwfWiremockWsdlUtility.stubOperation(
                "CreateInternalGI",
                CreateInternalGI.class, (u) -> true,
                response);

        val documentResponse = fabasoftAdapter.createDocument(new Document("procedureCOO", "title", DocumentType.INTERN, List.of(content)), "user");

        assertEquals(documentResponse, "documentCOO");
    }

    @Test
    void execute_updateIncomingDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new UpdateIncomingGIResponse();
        response.setObjid("documentCOO");

        DigiwfWiremockWsdlUtility.stubOperation(
                "UpdateIncomingGI",
                UpdateIncomingGI.class, (u) -> true,
                response);

        fabasoftAdapter.updateDocument("documentCOO", DocumentType.EINGEHEND, List.of(content), "user");
    }

    @Test
    void execute_updateOutgoingDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new UpdateOutgoingGIResponse();
        response.setObjid("documentCOO");

        DigiwfWiremockWsdlUtility.stubOperation(
                "UpdateOutgoingGI",
                UpdateOutgoingGI.class, (u) -> true,
                response);

        fabasoftAdapter.updateDocument("documentCOO", DocumentType.AUSGEHEND, List.of(content), "user");
    }

    @Test
    void execute_updateInternalDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new UpdateInternalGIResponse();
        response.setObjid("documentCOO");

        DigiwfWiremockWsdlUtility.stubOperation(
                "UpdateInternalGI",
                UpdateInternalGI.class, (u) -> true,
                response);

        fabasoftAdapter.updateDocument("documentCOO", DocumentType.INTERN, List.of(content), "user");
    }

    @Test
    void execute_cancelObject_request() {
        val response = new CancelObjectGIResponse();
        response.setStatus(0);

        DigiwfWiremockWsdlUtility.stubOperation(
                "CancelObjectGI",
                CancelObjectGI.class, (u) -> true,
                response);

        fabasoftAdapter.cancelObject("objectCoo", "user");
    }

    @Test
    void execute_read_files() {
        val content = new LHMBAI151700GIAttachmentType();
        content.setLHMBAI151700Filename("filename");
        content.setLHMBAI151700Fileextension("extension");
        content.setLHMBAI151700Filecontent("content".getBytes());

        val response = new ReadContentObjectGIResponse();
        response.setStatus(0);
        response.setGiattachmenttype(content);

        DigiwfWiremockWsdlUtility.stubOperation(
                "ReadContentObjectGI",
                CancelObjectGI.class, (u) -> true,
                response);

        val files = fabasoftAdapter.readContent(List.of("coo1"), "user");

        val expectedFile = new Content("extension", "filename", "content".getBytes());

        assertThat(files.size()).isEqualTo(1);
        assertThat(files.get(0)).usingRecursiveComparison().isEqualTo(expectedFile);
    }

    @Test
    void execute_searchFile_request() {
        val file = new LHMBAI151700GIObjectType();
        file.setLHMBAI151700Objaddress("testCoo");
        file.setLHMBAI151700Objname("testName");

        val array = new ArrayOfLHMBAI151700GIObjectType();
        array.getLHMBAI151700GIObjectType().add(file);

        val response = new SearchObjNameGIResponse();
        response.setStatus(0);
        response.setGiobjecttype(array);

        DigiwfWiremockWsdlUtility.stubOperation(
                "SearchObjNameGI",
                SearchObjNameGI.class, (u) -> u.getObjclass().equals(DMSObjectClass.Sachakte.getName()),
                response);

        val files = fabasoftAdapter.searchFile("searchString", "user");

        assertThat(files.size()).isEqualTo(1);
    }

    @Test
    void execute_searchSubjectArea_request() {
        val file = new LHMBAI151700GIObjectType();
        file.setLHMBAI151700Objaddress("testCoo");
        file.setLHMBAI151700Objname("testName");

        val array = new ArrayOfLHMBAI151700GIObjectType();
        array.getLHMBAI151700GIObjectType().add(file);

        val response = new SearchObjNameGIResponse();
        response.setStatus(0);
        response.setGiobjecttype(array);

        DigiwfWiremockWsdlUtility.stubOperation(
                "SearchObjNameGI",
                SearchObjNameGI.class, (u) -> u.getObjclass().equals(DMSObjectClass.Aktenplaneintrag.getName()),
                response);

        val files = fabasoftAdapter.searchSubjectArea("searchString", "user");

        assertThat(files.size()).isEqualTo(1);

    }


}
