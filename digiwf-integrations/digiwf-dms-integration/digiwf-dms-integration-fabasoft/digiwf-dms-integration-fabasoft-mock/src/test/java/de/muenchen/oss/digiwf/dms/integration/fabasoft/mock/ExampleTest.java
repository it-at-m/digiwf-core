package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.muenchen.oss.digiwf.integration.e2e.test.wsdl.DigiwfWiremockWsdlUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WireMockTest()
public class ExampleTest {

    private LHMBAI151700GIWSDSoap soapClient;


    @BeforeEach
    public void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        soapClient = FabasoftClienFactory.dmsWsClient("http://localhost:" + wmRuntimeInfo.getHttpPort() + "/");
    }

    @Test
    public void execute_createProcedure_request() {
        DigiwfWiremockWsdlUtility.stubOperation(
                "CreateProcedureGI",
                CreateProcedureGI.class, (u) -> "new procedure".equals(u.getShortname()),
                new CreateProcedureGIResponse());

        final CreateProcedureGI request = new CreateProcedureGI();
        request.setUserlogin("user");
        request.setReferrednumber("fileCOO");
        request.setBusinessapp("businessapp");
        request.setShortname("new procedure");
        request.setFilesubj("new procedure");
        request.setFiletype("Elektronisch");

        final CreateProcedureGIResponse response = this.soapClient.createProcedureGI(request);

    }


}
