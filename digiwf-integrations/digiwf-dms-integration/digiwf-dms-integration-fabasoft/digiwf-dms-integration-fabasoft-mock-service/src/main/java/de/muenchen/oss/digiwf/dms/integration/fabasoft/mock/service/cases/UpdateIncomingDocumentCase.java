package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.UpdateIncomingGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.UpdateIncomingGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;
import org.springframework.stereotype.Component;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

@Component
public class UpdateIncomingDocumentCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val updateIncomingGIResponse = new UpdateIncomingGIResponse();
        updateIncomingGIResponse.setObjid("1234567890");

        stubOperation(
                server,
                "UpdateIncomingGI",
                UpdateIncomingGI.class, (u) -> true,
                updateIncomingGIResponse);

    }

}
