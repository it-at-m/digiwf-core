package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateInternalGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateInternalGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

public class CreateInternalDocumentCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val createInternalGIResponse = new CreateInternalGIResponse();
        createInternalGIResponse.setObjid("1234567890");

        stubOperation(
                server,
                "CreateInternalGI",
                CreateInternalGI.class, (u) -> true,
                createInternalGIResponse);

    }

}
