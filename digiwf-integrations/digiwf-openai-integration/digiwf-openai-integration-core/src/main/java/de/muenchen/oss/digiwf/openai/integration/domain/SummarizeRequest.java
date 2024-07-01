package de.muenchen.oss.digiwf.openai.integration.domain;

import lombok.Value;

@Value
public class SummarizeRequest {

    private String text;
    private int length;
}
