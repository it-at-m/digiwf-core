package de.muenchen.oss.digiwf.connector;

import de.muenchen.oss.digiwf.spring.security.client.OAuth2AccessTokenSupplier;
import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;
import org.camunda.community.rest.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
@Profile("!no-security")
@AllArgsConstructor
public class CamundaSecurityConfig {

    private final OAuth2AccessTokenSupplier tokenSupplier;

    @Bean
    public ClientRequestInterceptor interceptor() {
        return context -> {
            context.addHeader("Authorization", this.getAccessToken());
        };
    }

    @Autowired
    public void addOAuthInterceptor(final ApiClient apiClient) {
        apiClient.setHttpClient(HttpClientBuilder.create().addRequestInterceptorFirst(this::intercept).build());
    }

    private void intercept(HttpRequest httpRequest, EntityDetails entityDetails, HttpContext httpContext) {
        httpRequest.addHeader("Authorization", this.getAccessToken());
    }

    public Response intercept(final Interceptor.Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request requestWithToken = originalRequest.newBuilder()
                .header("Authorization", this.getAccessToken())
                .build();
        return chain.proceed(requestWithToken);
    }

    public String getAccessToken() {
        return "Bearer " + this.tokenSupplier.get().getTokenValue();
    }
}
