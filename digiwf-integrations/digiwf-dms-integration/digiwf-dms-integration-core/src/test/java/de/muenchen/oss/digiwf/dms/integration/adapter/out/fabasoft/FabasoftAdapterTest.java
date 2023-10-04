package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.FabasoftClienFactory;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.muenchen.oss.digiwf.dms.integration.fabasoft.mock.MockUtils.stubOperation;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest()
public class FabasoftAdapterTest {

    private final FabasoftProperties properties = new FabasoftProperties();
    private LHMBAI151700GIWSDSoap soapClient;
    private FabasoftAdapter fabasoftAdapter;


    @BeforeEach
    public void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        this.properties.setUsername("user");
        this.properties.setPassword("password");
        this.properties.setBusinessapp("businessapp");
        soapClient = FabasoftClienFactory.dmsWsClient("http://localhost:" + wmRuntimeInfo.getHttpPort() + "/");
        fabasoftAdapter = new FabasoftAdapter(properties, this.soapClient);
    }

    @Test
    public void execute_createProcedure_request() {
        val response = new CreateProcedureGIResponse();
        response.setObjid("1234567890");

        stubOperation(
                "CreateProcedureGI",
                CreateProcedureGI.class, (u) -> "new procedure".equals(u.getShortname()),
                response);

        val procedure = new Procedure("fileCOO", "new procedure");

        val procedureResponse = fabasoftAdapter.createProcedure(procedure, "user");

        assertEquals(procedureResponse.getCoo(), "1234567890");
    }


}
