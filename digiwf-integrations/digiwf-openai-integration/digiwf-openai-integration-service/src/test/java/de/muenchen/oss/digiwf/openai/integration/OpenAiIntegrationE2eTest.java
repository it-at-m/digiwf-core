package de.muenchen.oss.digiwf.openai.integration;


import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.muenchen.oss.digiwf.integration.e2e.test.DigiwfE2eTest;
import de.muenchen.oss.digiwf.integration.e2e.test.DigiwfIntegrationE2eTestUtility;
import de.muenchen.oss.digiwf.integration.e2e.test.DigiwfWiremockUtility;
import de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto.PromptDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * E2e tests for openai-integration-service using embedded kafka and wiremock to fake infrastructure components
 */
@DigiwfE2eTest
@WireMockTest(httpPort = 8189)
class OpenAiIntegrationE2eTest {

    private String processInstanceId;

    @Autowired
    private DigiwfIntegrationE2eTestUtility digiWFIntegrationE2eTestUtility;

    @BeforeEach
    void setup() {
        this.processInstanceId = UUID.randomUUID().toString();
    }

    @Test
    void shouldStart() {
        // test fails if application context can not start
    }

    @Test
    void testBasicChat() {
        final PromptDto promptDto = PromptDto.builder()
                .prompt("test")
                .build();
        final String expectedResponse = """
                {
                    "choices": [
                        {
                            "finish_reason": "length",
                            "index": 0,
                            "message": {
                                "content": "Hello! Is there anything",
                                "role": "assistant"
                            }
                        }
                    ],
                    "created": 1713517443,
                    "id": "chatcmpl-9FeS3T1S8tW6dPWZ8KJUaUA4yCcPr",
                    "model": "gpt-35-turbo",
                    "object": "chat.completion",
                    "system_fingerprint": null,
                    "usage": {
                        "completion_tokens": 5,
                        "prompt_tokens": 19,
                        "total_tokens": 24
                    }
                }
                """;
        final String expectedRequest = """
                {
                    "model": "gpt-35-turbo",
                    "messages": [
                        { "role": "system", "content": "You are a polite assistant" },
                        { "role": "user", "content": "test" }
                    ],
                    "temperature": 0.7,
                    "max_tokens": 5
                }
                """;
        DigiwfWiremockUtility.setupPOST("/chat/completions",expectedRequest, expectedResponse);

        final Map<String, Object> payload = this.digiWFIntegrationE2eTestUtility.runIntegration(promptDto, processInstanceId, "openAiIntegration", "basicChat");

        assertThat(payload).isNotNull();
    }

}
