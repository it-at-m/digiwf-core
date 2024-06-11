package de.muenchen.oss.digiwf.adapter.out.ldap;

import de.muenchen.oss.digiwf.LdapTestConfiguration;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    }

    @Test
    void testResolveGroups() {

    }
}
