package de.muenchen.oss.digiwf.address.integration;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import de.muenchen.oss.digiwf.address.integration.adapter.in.streaming.dto.SearchAdressenDeutschlandDto;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresseResponse;
import de.muenchen.oss.digiwf.address.integration.client.gen.model.BundesweiteAdresseResponseItem;
import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@DirtiesContext
//@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:29092" })
//@WireMockTest(httpPort = 8089)
class AddressIntegrationE2eTest {

    @Autowired
    private MessageApi messageApi;
    @Autowired
    private TestMessageConsumer testMessageConsumer;


    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String messageTopic = "dwf-address-e2e-test";

//    @Test
    void testSearchAddressesGermany() throws InterruptedException, JsonProcessingException {
        // TODO fixme
        final String messageType = "searchAddressesGermany";
        final SearchAdressenDeutschlandDto searchAdressenDeutschlandDto = SearchAdressenDeutschlandDto.builder()
                .ortsname("Muenchen")
                .build();

        // TODO get real responses from master
        final String r = "{\"page\":null,\"content\":[{\"score\":null,\"adresse\":{\"buchstabe\":null,\"hausnummer\":null,\"ortsname\":\"Muenchen\",\"strasseId\":null,\"strassenname\":null,\"position\":null,\"adresse\":null,\"geozuordnungen\":null}}]}";
        WireMock.stubFor(WireMock.get("/v2/adresse_bundesweit/search?ortsname=Muenchen")
                .willReturn(WireMock.aResponse().withBody(r).withStatus(200)));

        // send and receive messages
        this.sendMessage(searchAdressenDeutschlandDto, messageType);
        final Map<String, Object> payload = this.testMessageConsumer.awaitMessage(messageType);

        // assert
        assertNotNull(payload);
        assertTrue(payload.containsKey("response"));
        final Object response = payload.get("response");
        assertTrue(response instanceof BundesweiteAdresseResponse);
        final Optional<BundesweiteAdresseResponseItem> resp = ((BundesweiteAdresseResponse) response).getContent().stream().findFirst();

        assertTrue(resp.isPresent());
        assertEquals(searchAdressenDeutschlandDto.getOrtsname(), resp.get().getAdresse().getOrtsname());
    }

    private void sendMessage(final Object payload, final String messageType) {
        final Map<String, Object> headers = Map.of(DIGIWF_PROCESS_INSTANCE_ID, "exampleProcessInstanceId", DIGIWF_MESSAGE_NAME, "messageName", TYPE, messageType);
        messageApi.sendMessage(payload, headers, messageTopic);
    }

}
