package de.muenchen.oss.digiwf.ocecosmos.integration.api.dto.request;

import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.DataTypes;
import de.muenchen.oss.digiwf.ocecosmos.integration.model.request.DeliveryTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseJobRequestDto {

    String jobType;

    DeliveryTypes deliveryType;

    DataTypes dataType;

    String applicationName;

    String debtor;

    String email;

    String pickupLocation;

    Boolean printJob;

    Boolean mailJob;

    Boolean archiveJob;

    String userId;

    String hostName;
}
