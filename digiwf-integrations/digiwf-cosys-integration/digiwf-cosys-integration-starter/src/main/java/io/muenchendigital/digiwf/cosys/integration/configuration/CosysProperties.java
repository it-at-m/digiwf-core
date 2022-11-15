package io.muenchendigital.digiwf.cosys.integration.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "io.muenchendigital.digiwf.cosys")
public class CosysProperties {

    @NestedConfigurationProperty
    private MergeProperties merge;

    @NotBlank
    private String url;

    @NotBlank
    private String topic;

    @NotBlank
    private String engineTopic;

    @Getter
    @Setter
    public static class MergeProperties {

        private String datafile;

        private String inputLanguage;

        private String outputLanguage;

        private String keepFields;

    }
}
