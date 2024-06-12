package de.muenchen.oss.digiwf.adapter.out.ldap;

import de.muenchen.oss.digiwf.LdapTestConfiguration;
import de.muenchen.oss.digiwf.domain.Group;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"groups-ldap"})
@ContextConfiguration(classes = {LdapTestConfiguration.class})
class LdapAdapterTest {
    private final LdapAdapter ldapAdapter;

    LdapAdapterTest(@Autowired final ContextSource contextSource) {
        val ldapProperties = new LdapProperties();
        ldapProperties.setGroupBase("OU=groups,DC=muenchen,DC=de");
        ldapProperties.setUserBase("OU=users,DC=muenchen,DC=de");
        this.ldapAdapter = new LdapAdapter(contextSource, ldapProperties);
    }

    @Test
    void testResolveUserGroups() {
        // test success
        val groups = ldapAdapter.resolveUserGroups("test.user2");
        val groupNames = groups.stream().map(Group::name).toList();
        val shouldMatchGroups = List.of("test-group", "test-group2");
        assertEquals(shouldMatchGroups, groupNames);
        // test user not existing
        assertThrows(IllegalStateException.class, () -> ldapAdapter.resolveUserGroups("test.user3"));
    }

    @Test
    void testResolveUser() {
        // test success
        val user = ldapAdapter.resolveUser("test.user");
        assertEquals("test.user", user.getId());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("test.user@muenchen.de", user.getEmail());
        // test not found
        val userNotFound = ldapAdapter.resolveUser("test.user3");
        assertNull(userNotFound);
    }

    @Test
    void testGetGroupsMembers() {
        val members = ldapAdapter.getGroupsMembers(List.of(new Group("test-group")));
        assertEquals(List.of("test.user", "test.user2"), members);
    }
}
