package de.muenchen.oss.digiwf.s3.integration.example.client.streaming.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePresignedUrlEvent {

    private String action;
    private String path;

}
