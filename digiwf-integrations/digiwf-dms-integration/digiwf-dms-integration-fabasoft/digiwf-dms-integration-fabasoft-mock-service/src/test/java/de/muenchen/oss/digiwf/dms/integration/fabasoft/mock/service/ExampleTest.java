package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.FabasoftClienFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = FabasoftMockApplication.class)
public class ExampleTest {

    private final LHMBAI151700GIWSDSoap soapClient = FabasoftClienFactory.dmsWsClient("http://localhost:8080/");

    @Test
    public void execute_createProcedure_request() {
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
