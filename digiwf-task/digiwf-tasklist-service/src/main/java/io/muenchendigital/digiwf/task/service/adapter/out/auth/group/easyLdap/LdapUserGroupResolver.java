package io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap;

import feign.FeignException;
import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap.model.UserInfoResponse;
import io.muenchendigital.digiwf.task.service.application.port.out.auth.UserGroupResolverPort;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class LdapUserGroupResolver implements UserGroupResolverPort {

    private final EasyLdapClient easyLdapClient;

    @NotNull
    @Override
    public Set<String> resolveGroups(String lhmObjectId) {
        try {
            UserInfoResponse userInfoResponse = easyLdapClient.getUserById(lhmObjectId);
            return Collections.singleton(userInfoResponse.getOu());
        } catch (FeignException e) {
            return Collections.emptySet();
        }
    }
}
