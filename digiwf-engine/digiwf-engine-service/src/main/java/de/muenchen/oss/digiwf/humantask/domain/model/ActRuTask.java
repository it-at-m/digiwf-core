package de.muenchen.oss.digiwf.humantask.domain.model;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ActRuTask {
    private String id;
    private String assignee;
    private String name;
    private Date createdAt;
    private Date followUpDate;
    private TaskInfo taskInfo;
}
