package io.muenchendigital.digiwf.taskana.connector.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDeletionDto {

    private String id;

    private String state;
}
