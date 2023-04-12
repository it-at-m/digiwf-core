package io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class UserInfoResponse {

    /**
     * Unique id of the user
     */
    private String lhmObjectId;

    /**
     * User group
     */
    private String ou;

    /**
     * List of user group and the superior organizational units as a string
     */
    private String lhmObjectPath;

}
