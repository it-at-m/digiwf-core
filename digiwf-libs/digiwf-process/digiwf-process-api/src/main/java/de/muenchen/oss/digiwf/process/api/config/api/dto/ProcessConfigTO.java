package de.muenchen.oss.digiwf.process.api.config.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessConfigTO {

    /**
     * key of the process config.
     */
    @NotBlank
    private String key;

    /**
     * default status dokument.
     */
    private String statusDokument;

    /**
     * status config of the process definition.
     */
    @Builder.Default
    private List<StatusConfigTO> statusConfig = new ArrayList<>();

    /**
     * dynamic config entries.
     */
    @Builder.Default
    private List<ConfigEntryTO> configs = new ArrayList<>();
}
