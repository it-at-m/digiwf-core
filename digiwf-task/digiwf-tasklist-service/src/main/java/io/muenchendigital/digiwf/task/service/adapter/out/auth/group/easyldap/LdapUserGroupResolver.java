package io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyldap;

import com.google.common.collect.Sets;
import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyldap.model.UserInfoResponse;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.UserGroupResolverPort;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class LdapUserGroupResolver implements UserGroupResolverPort {
    EasyLdapClient easyLdapClient;
    @NotNull
    @Override
    public Set<String> resolveGroups(String lhmObjectId) {
        try {
            UserInfoResponse userInfoResponse = easyLdapClient.getUserById(lhmObjectId);
            return Sets.newHashSet(userInfoResponse.getOu());
        } catch (Exception e) {
            return Sets.newHashSet();
        }
    }
}
