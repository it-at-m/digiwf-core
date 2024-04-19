package de.muenchen.oss.digiwf.openai.integration.adapter.out.dto;

import lombok.Value;

@Value
public class MapJsonRequest {

    private String json;
    private String type;
}
