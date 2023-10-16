package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CancelObjectGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CancelObjectGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;
import org.springframework.stereotype.Component;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

@Component
public class CancelObjectCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val cancelObjectGIResponse = new CancelObjectGIResponse();
        cancelObjectGIResponse.setStatus(0);

        stubOperation(
                server,
                "CancelObjectGI",
                CancelObjectGI.class, (u) -> true,
                cancelObjectGIResponse);

    }

}
