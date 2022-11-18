package io.muenchendigital.digiwf.humantask.domain.model;

import lombok.*;

import java.util.Date;
// FIXME is javadoc really necessary?
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
}
