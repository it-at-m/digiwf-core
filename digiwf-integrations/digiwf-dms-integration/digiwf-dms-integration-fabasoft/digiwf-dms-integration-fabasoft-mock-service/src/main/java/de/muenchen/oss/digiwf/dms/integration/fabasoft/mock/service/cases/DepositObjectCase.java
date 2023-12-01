package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.DepositObjectGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;
import org.springframework.stereotype.Component;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

@Component
public class DepositObjectCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val depositObjectGIResponse = new DepositObjectGIResponse();
        depositObjectGIResponse.setObjid("1234567890");

        stubOperation(
                server,
                "DepositObjectGI",
                CreateProcedureGI.class, (u) -> true,
                depositObjectGIResponse);

    }

}
