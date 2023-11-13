package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateFileGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateFileGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;
import org.springframework.stereotype.Component;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

@Component
public class CreateFileCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val createFileGIResponse = new CreateFileGIResponse();
        createFileGIResponse.setObjid("1234567890");

        stubOperation(
                server,
                "CreateFileGI",
                CreateFileGI.class, (u) -> true,
                createFileGIResponse);

    }

}
