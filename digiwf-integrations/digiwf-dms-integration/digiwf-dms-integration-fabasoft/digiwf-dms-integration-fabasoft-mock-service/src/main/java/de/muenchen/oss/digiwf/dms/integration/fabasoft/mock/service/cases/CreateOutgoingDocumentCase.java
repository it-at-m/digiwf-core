package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateOutgoingGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateOutgoingGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;
import org.springframework.stereotype.Component;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

@Component
public class CreateOutgoingDocumentCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val createOutgoingGIResponse = new CreateOutgoingGIResponse();
        createOutgoingGIResponse.setObjid("1234567890");

        stubOperation(
                server,
                "CreateOutgoingGI",
                CreateOutgoingGI.class, (u) -> true,
                createOutgoingGIResponse);

    }

}
