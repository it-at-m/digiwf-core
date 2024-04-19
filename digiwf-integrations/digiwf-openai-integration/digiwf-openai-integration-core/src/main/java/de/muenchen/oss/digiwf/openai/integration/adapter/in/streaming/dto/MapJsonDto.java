package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto;

import lombok.Value;

@Value
public class MapJsonDto {

    private String json;
    private String type;
}
