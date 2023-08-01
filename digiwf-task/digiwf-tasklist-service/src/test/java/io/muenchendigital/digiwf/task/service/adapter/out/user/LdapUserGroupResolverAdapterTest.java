package io.muenchendigital.digiwf.task.service.adapter.out.user;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import io.muenchendigital.digiwf.task.service.adapter.out.user.easyldap.EasyLdapClient;
import io.muenchendigital.digiwf.task.service.application.port.out.user.UserGroupResolverPort;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LdapUserGroupResolverAdapterTest {

    private final EasyLdapClient easyLdapClient = Mockito.mock(EasyLdapClient.class);
    private final UserGroupResolverPort ldapUserGroupResolver = new LdapUserGroupResolverAdapter(this.easyLdapClient);


    @Test
    public void resolve_group() {
        val userId = "1234";
        when(this.easyLdapClient.getOuTreeByUserId(any())).thenReturn(List.of("LHM", "RIT", "ITM"));
        final Set<String> groups = this.ldapUserGroupResolver.resolveGroups(userId);

        assertEquals(Set.of("lhm", "rit", "itm"), groups);
        verify(this.easyLdapClient).getOuTreeByUserId(userId);
        verifyNoMoreInteractions(this.easyLdapClient);
    }

    @Test
    public void fail_to_resolve_group() {
        val userId = "0";
        final var request = Request.create(Request.HttpMethod.GET, "url",
                new HashMap<>(), null, new RequestTemplate());
        when(this.easyLdapClient.getOuTreeByUserId(any())).thenThrow(new FeignException.NotFound("", request, null, null));
        final Set<String> groups = this.ldapUserGroupResolver.resolveGroups(userId);
        assertTrue(groups.isEmpty());
        verify(this.easyLdapClient).getOuTreeByUserId(userId);
        verifyNoMoreInteractions(this.easyLdapClient);
    }


}
