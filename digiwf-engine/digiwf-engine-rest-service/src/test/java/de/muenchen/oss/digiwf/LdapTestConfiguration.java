package de.muenchen.oss.digiwf;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.schema.Schema;
import com.unboundid.ldif.LDIFException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.LdapContextSource;

import java.io.IOException;

@Configuration
public class LdapTestConfiguration {
    @Autowired
    private ContextSource contextSource;

    @Bean
    InMemoryDirectoryServer ldapServer() throws LDAPException, IOException, LDIFException {
        final InMemoryListenerConfig listenerConfig = InMemoryListenerConfig.createLDAPConfig(
                "default", 0);
        final InMemoryDirectoryServerConfig c = new InMemoryDirectoryServerConfig(
                "dc=muenchen,dc=de");
        c.setListenerConfigs(listenerConfig);
        c.setEnforceAttributeSyntaxCompliance(false);
        c.setEnforceSingleStructuralObjectClass(false);
        // LHM Schema einspielen
        final ClassPathResource schemaResource = new ClassPathResource("ldap_schema.ldif");
        val schema = Schema.getSchema(schemaResource.getFile());
        c.setSchema(Schema.getSchema(schemaResource.getFile()));
        final InMemoryDirectoryServer inMemoryDirectoryServer = new InMemoryDirectoryServer(c);
        final ClassPathResource dataResource = new ClassPathResource("ldap_test_data.ldif");
        inMemoryDirectoryServer.applyChangesFromLDIF(dataResource.getFile());
        inMemoryDirectoryServer.startListening();
        return inMemoryDirectoryServer;
    }


    @Bean
    public ContextSource contextSource() throws LDAPException, LDIFException, IOException {
        final LdapContextSource ldapContextSource = new LdapContextSource();
        final int listenPort = ldapServer().getListenPort();
        ldapContextSource.setUrl("ldap://localhost:" + listenPort);
        return ldapContextSource;
    }
}
