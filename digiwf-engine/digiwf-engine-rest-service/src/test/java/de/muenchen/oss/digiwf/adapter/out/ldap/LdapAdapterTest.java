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

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test", "groups-ldap"})
@ContextConfiguration(classes = {LdapTestConfiguration.class})
class LdapAdapterTest {
    private final LdapProperties ldapProperties;
    @Autowired
    private ContextSource contextSource;

    LdapAdapterTest() {
        this.ldapProperties = new LdapProperties();
        this.ldapProperties.setGroupBase("OU=groups,DC=muenchen,DC=de");
        this.ldapProperties.setUserBase("OU=users,DC=muenchen,DC=de");
    }

    @Test
    void testResolveUser() {
        val ldapAdapter = new LdapAdapter(contextSource, ldapProperties);
        val user = ldapAdapter.resolveUser("test.user");
    }
}