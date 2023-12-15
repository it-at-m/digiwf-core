package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.UpdateInternalGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.UpdateInternalGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import de.muenchen.oss.digiwf.integration.e2e.test.wsdl.DigiwfWiremockWsdlUtility;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class UpdateInternalDocumentCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val updateInternalGIResponse = new UpdateInternalGIResponse();
        updateInternalGIResponse.setObjid("1234567890");

        DigiwfWiremockWsdlUtility.stubOperation(
                server,
                "UpdateInternalGI",
                UpdateInternalGI.class, (u) -> true,
                updateInternalGIResponse);

    }

}
