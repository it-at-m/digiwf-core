package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.UpdateOutgoingGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.UpdateOutgoingGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

public class UpdateOutgoingDocumentCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val updateOutgoingGIResponse = new UpdateOutgoingGIResponse();
        updateOutgoingGIResponse.setObjid("1234567890");

        stubOperation(
                server,
                "UpdateOutgoingGI",
                UpdateOutgoingGI.class, (u) -> true,
                updateOutgoingGIResponse);

    }

}
