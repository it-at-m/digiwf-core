package io.muenchendigital.digiwf.integration.cosys.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CosysConfiguration {

    private byte[] mergeOptions;

    private String url;

    private String ssoTokenRequestUrl;

    private String ssoTokenClientId;

    private String ssoTokenClientSecret;

}
