package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateOutgoingGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateOutgoingGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import de.muenchen.oss.digiwf.integration.e2e.test.wsdl.DigiwfWiremockWsdlUtility;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class CreateOutgoingDocumentCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val createOutgoingGIResponse = new CreateOutgoingGIResponse();
        createOutgoingGIResponse.setObjid("1234567890");

        DigiwfWiremockWsdlUtility.stubOperation(
                server,
                "CreateOutgoingGI",
                CreateOutgoingGI.class, (u) -> true,
                createOutgoingGIResponse);

    }

}
