package io.muenchendigital.digiwf.task.service.adapter.out.auth.group;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap.EasyLdapClient;
import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap.LdapUserGroupResolver;
import io.muenchendigital.digiwf.task.service.adapter.out.auth.group.easyLdap.model.UserInfoResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LdapUserGroupResolverTest {

    private final EasyLdapClient easyLdapClient = Mockito.mock(EasyLdapClient.class);
    private final LdapUserGroupResolver ldapUserGroupResolver = new LdapUserGroupResolver(easyLdapClient);


    @Test
    public void getHashWithUserGroup() {
        Mockito.when(easyLdapClient.getUserById("1234")).thenReturn(new UserInfoResponse("1234", "OU", "Path"));
        Set<String> groups = ldapUserGroupResolver.resolveGroups("1234");

        assertEquals(1, groups.size());
        assertTrue(groups.contains("OU"));

    }

    @Test
    public void getEmptyHash() {
        var request = Request.create(Request.HttpMethod.GET, "url",
                new HashMap<>(), null, new RequestTemplate());
        Mockito.when(easyLdapClient.getUserById("0")).thenThrow(new FeignException.NotFound("", request, null, null));
        Set<String> groups = ldapUserGroupResolver.resolveGroups("0");
        assertTrue(groups.isEmpty());
    }


}
