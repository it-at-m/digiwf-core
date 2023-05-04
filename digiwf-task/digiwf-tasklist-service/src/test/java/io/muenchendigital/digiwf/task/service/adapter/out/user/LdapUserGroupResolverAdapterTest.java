package io.muenchendigital.digiwf.task.service.adapter.out.user;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import io.muenchendigital.digiwf.task.service.adapter.out.user.easyldap.EasyLdapClient;
import io.muenchendigital.digiwf.task.service.adapter.out.user.easyldap.UserInfoResponse;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserGroupResolverPort;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LdapUserGroupResolverAdapterTest {

    private final EasyLdapClient easyLdapClient = Mockito.mock(EasyLdapClient.class);
    private final UserGroupResolverPort ldapUserGroupResolver = new LdapUserGroupResolverAdapter(easyLdapClient);


    @Test
    public void resolve_group() {
        val userId = "1234";
        when(easyLdapClient.getUserById(any())).thenReturn(new UserInfoResponse(userId, "OU", "Path", "First", "Last"));
        Set<String> groups = ldapUserGroupResolver.resolveGroups(userId);

        assertEquals(1, groups.size());
        assertTrue(groups.contains("OU"));
        verify(easyLdapClient).getUserById(userId);
        verifyNoMoreInteractions(easyLdapClient);
    }

    @Test
    public void fail_to_resolve_group() {
        val userId = "0";
        var request = Request.create(Request.HttpMethod.GET, "url",
                new HashMap<>(), null, new RequestTemplate());
        when(easyLdapClient.getUserById(any())).thenThrow(new FeignException.NotFound("", request, null, null));
        Set<String> groups = ldapUserGroupResolver.resolveGroups(userId);
        assertTrue(groups.isEmpty());
        verify(easyLdapClient).getUserById(userId);
        verifyNoMoreInteractions(easyLdapClient);
    }


}
