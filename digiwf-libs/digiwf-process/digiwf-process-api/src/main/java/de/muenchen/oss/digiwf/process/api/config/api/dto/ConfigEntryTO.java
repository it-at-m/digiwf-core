package de.muenchen.oss.digiwf.process.api.config.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigEntryTO {

    /**
     * Key of the config entry.
     */
    @NotBlank
    private String key;

    /**
     * Value of the config.
     */
    @NotBlank
    private String value;

}
