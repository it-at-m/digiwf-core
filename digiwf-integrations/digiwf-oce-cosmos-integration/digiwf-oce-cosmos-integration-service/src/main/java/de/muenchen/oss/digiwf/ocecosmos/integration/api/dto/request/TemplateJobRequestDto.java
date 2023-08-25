package de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TemplateJobRequestDto extends BaseJobRequestDto {

    String templateName;

    String fileName;

    byte[] file;
}
