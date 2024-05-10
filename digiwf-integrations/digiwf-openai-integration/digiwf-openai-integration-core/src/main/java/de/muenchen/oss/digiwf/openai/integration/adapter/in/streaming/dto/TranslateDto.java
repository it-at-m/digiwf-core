package de.muenchen.oss.digiwf.openai.integration.adapter.in.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateDto {

    private String text;
    private String language;
}
