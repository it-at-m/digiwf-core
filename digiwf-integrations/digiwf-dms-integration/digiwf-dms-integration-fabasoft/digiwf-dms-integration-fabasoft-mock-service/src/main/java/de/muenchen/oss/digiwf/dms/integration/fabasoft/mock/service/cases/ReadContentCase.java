package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CancelObjectGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIAttachmentType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.ReadContentObjectGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

@Component
public class ReadContentCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val content = new LHMBAI151700GIAttachmentType();
        content.setLHMBAI151700Filename("test");
        content.setLHMBAI151700Fileextension("pdf");

        try {
            Resource resource = new ClassPathResource("data/test.pdf");
            byte[] data = resource.getInputStream().readAllBytes();
            content.setLHMBAI151700Filecontent(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        val response = new ReadContentObjectGIResponse();
        response.setStatus(0);
        response.setGiattachmenttype(content);

        stubOperation(
                server,
                "ReadContentObjectGI",
                CancelObjectGI.class, (u) -> true,
                response);

    }

}
