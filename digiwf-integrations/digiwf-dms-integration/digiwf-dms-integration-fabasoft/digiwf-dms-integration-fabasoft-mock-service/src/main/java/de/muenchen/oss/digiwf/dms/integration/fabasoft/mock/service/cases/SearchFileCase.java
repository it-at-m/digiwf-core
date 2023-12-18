package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.ArrayOfLHMBAI151700GIObjectType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIObjectType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.SearchObjNameGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.SearchObjNameGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import de.muenchen.oss.digiwf.integration.e2e.test.wsdl.DigiwfWiremockWsdlUtility;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class SearchFileCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val file = new LHMBAI151700GIObjectType();
        file.setLHMBAI151700Objaddress("fileCoo");
        file.setLHMBAI151700Objname("fileName");

        val array = new ArrayOfLHMBAI151700GIObjectType();
        array.getLHMBAI151700GIObjectType().add(file);

        val response = new SearchObjNameGIResponse();
        response.setStatus(0);
        response.setGiobjecttype(array);

        DigiwfWiremockWsdlUtility.stubOperation(
                server,
                "SearchObjNameGI",
                SearchObjNameGI.class, (u) -> u.getObjclass().equals(DMSObjectClass.Sachakte.getName()),
                response);
    }

}
