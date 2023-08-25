package de.muenchen.oss.digiwf.ocecosmos.integration.model.request;

import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;

@Data
@Builder
public class JobRequest {

    String clientId;

    String jobType;

    String templateName;

    DeliveryTypes deliveryType;

    DataTypes dataType;

    String applicationName;

    String debtor;

    String email;

    String pickupLocation;

    boolean printJob;

    boolean mailJob;

    boolean archiveJob;

    String fileName;

    Path file;

    String userId;

    String hostName;
}
