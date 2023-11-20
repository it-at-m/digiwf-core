package de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.service.cases;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.ArrayOfLHMBAI151700GIObjectType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIObjectType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.SearchObjNameGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.SearchObjNameGIResponse;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.val;
import org.springframework.stereotype.Component;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;

@Component
public class SearchSubjectAreaCase implements MockCase {

    @Override
    public void initCase(WireMockServer server) {

        val file = new LHMBAI151700GIObjectType();
        file.setLHMBAI151700Objaddress("subjectAreaCoo");
        file.setLHMBAI151700Objname("subjectAreaName");

        val array = new ArrayOfLHMBAI151700GIObjectType();
        array.getLHMBAI151700GIObjectType().add(file);

        val response = new SearchObjNameGIResponse();
        response.setStatus(0);
        response.setGiobjecttype(array);

        stubOperation(
                server,
                "SearchObjNameGI",
                SearchObjNameGI.class, (u) -> u.getObjclass().equals(DMSObjectClass.Aktenplaneintrag.getName()),
                response);
    }

}
