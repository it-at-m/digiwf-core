package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.serializeObject;

@WireMockTest(httpPort = 8080)
public class ExampleTest {

    private final LHMBAI151700GIWSDSoap soapClient = FabasoftClienFactory.dmsWsClient("http://localhost:8080/");

    @Test
    public void test() {
        this.stubOperation(
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


    public <T> void stubOperation(String operation, Class<T> clazz, Predicate<T> predicate, Object response) {
        stubFor(requestMatching(new SoapObjectMatcher<>(clazz, operation, predicate))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/xml")
                        .withBody(serializeObject(response))));
    }

}
