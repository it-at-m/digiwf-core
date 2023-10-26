package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.*;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.FabasoftClienFactory;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest()
public class FabasoftAdapterTest {

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
    public void execute_createProcedure_request() {
        val response = new CreateProcedureGIResponse();
        response.setObjid("1234567890");

        stubOperation(
                "CreateProcedureGI",
                CreateProcedureGI.class, (u) -> "new procedure".equals(u.getShortname()),
                response);

        val procedure = new Procedure("fileCOO", "new procedure");

        val procedureResponse = fabasoftAdapter.createProcedure(procedure, "user");

        assertEquals(procedureResponse.getCoo(), "1234567890");
    }

    @Test
    public void execute_depositObject_request() {
        val response = new DepositObjectGIResponse();
        response.setObjid("objectCoo");

        stubOperation(
                "DepositObjectGI",
                DepositObjectGI.class, (u) -> true,
                response);


        fabasoftAdapter.depositObject("objectCoo", "user");
    }

    @Test
    public void execute_createIncomingDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new CreateIncomingGIResponse();
        response.setObjid("documentCOO");

        stubOperation(
                "CreateIncomingGI",
                CreateIncomingGI.class, (u) -> true,
                response);

        val documentResponse = fabasoftAdapter.createDocument(new Document("procedureCOO", "title", DocumentType.EINGEHEND, List.of(content)), "user");

        assertEquals(documentResponse, "documentCOO");
    }

    @Test
    public void execute_createOutgoingDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new CreateOutgoingGIResponse();
        response.setObjid("documentCOO");

        stubOperation(
                "CreateOutgoingGI",
                CreateOutgoingGI.class, (u) -> true,
                response);

        val documentResponse = fabasoftAdapter.createDocument(new Document("procedureCOO", "title", DocumentType.AUSGEHEND, List.of(content)), "user");

        assertEquals(documentResponse, "documentCOO");
    }

    @Test
    public void execute_createInternalDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new CreateInternalGIResponse();
        response.setObjid("documentCOO");

        stubOperation(
                "CreateInternalGI",
                CreateInternalGI.class, (u) -> true,
                response);

        val documentResponse = fabasoftAdapter.createDocument(new Document("procedureCOO", "title", DocumentType.INTERN, List.of(content)), "user");

        assertEquals(documentResponse, "documentCOO");
    }

    @Test
    public void execute_updateIncomingDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new UpdateIncomingGIResponse();
        response.setObjid("documentCOO");

        stubOperation(
                "UpdateIncomingGI",
                UpdateIncomingGI.class, (u) -> true,
                response);

        fabasoftAdapter.updateDocument("documentCOO", DocumentType.EINGEHEND, List.of(content), "user");
    }

    @Test
    public void execute_updateOutgoingDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new UpdateOutgoingGIResponse();
        response.setObjid("documentCOO");

        stubOperation(
                "UpdateOutgoingGI",
                UpdateOutgoingGI.class, (u) -> true,
                response);

        fabasoftAdapter.updateDocument("documentCOO", DocumentType.AUSGEHEND, List.of(content), "user");
    }

    @Test
    public void execute_updateInternalDocument_request() {
        Content content = new Content("extension", "name", "content".getBytes());

        val response = new UpdateInternalGIResponse();
        response.setObjid("documentCOO");

        stubOperation(
                "UpdateInternalGI",
                UpdateInternalGI.class, (u) -> true,
                response);

        fabasoftAdapter.updateDocument("documentCOO", DocumentType.INTERN, List.of(content), "user");
    }

    @Test
    public void execute_cancelObject_request() {
        val response = new CancelObjectGIResponse();
        response.setStatus(0);

        stubOperation(
                "CancelObjectGI",
                CancelObjectGI.class, (u) -> true,
                response);


        fabasoftAdapter.cancelObject("objectCoo", "user");
    }


}
