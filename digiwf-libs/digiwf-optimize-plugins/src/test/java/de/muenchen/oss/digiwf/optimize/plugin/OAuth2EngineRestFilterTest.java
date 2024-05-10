package de.muenchen.oss.digiwf.optimize.plugin;

import com.nimbusds.oauth2.sdk.ParseException;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OAuth2EngineRestFilterTest {
    private final OAuth2Client oAuth2Client = mock(OAuth2Client.class);
    private final OAuth2EngineRestFilter filter = new OAuth2EngineRestFilter(oAuth2Client);

    @Test
    void testTokenHeaderIsAdded() throws URISyntaxException, IOException, ParseException {
        val requestContext = mock(ClientRequestContext.class);
        when(oAuth2Client.getAccessToken()).thenReturn("Bearer accessToken");
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        when(requestContext.getHeaders()).thenReturn(headers);
        filter.filter(requestContext, "engineAlias", "engineName");
        assertEquals(1, headers.size());
        assertEquals("Bearer accessToken", headers.getFirst("Authorization"));
    }
}