/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.user.external.configuration;

import io.muenchendigital.digiwf.legacy.user.external.client.query.LdapFilterFactory;
import io.muenchendigital.digiwf.legacy.user.external.client.query.LdapQueryFactory;
import io.muenchendigital.digiwf.legacy.user.external.client.LhmLdapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * This class provides the LdapTemplate as a Bean for Ldap querying purposes.
 * <p>
 * Created by alexander.boxhorn on 07.06.17.
 */
@Configuration
@RequiredArgsConstructor
public class LdapTemplateConfiguration {

    private final ServiceAuthLdapProperties serviceAuthLdapProperties;

    /**
     * The context for the LdapTemplate.
     * The context in which Ldap the search should be performed und what search base should be used.
     *
     * @return The LdapContextSource for a LdapTemplate
     */
    @Bean
    public LdapContextSource contextSourceTarget() {
        final LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(this.serviceAuthLdapProperties.getContextSource());
        return ldapContextSource;
    }

    /**
     * Factory to create ldap queries.
     *
     * @param ldapFilterFactory factory to create ldap filters
     * @return the query factory
     */
    @Bean
    public LdapQueryFactory ldapQueryFactory(final LdapFilterFactory ldapFilterFactory){
        return new LdapQueryFactory(ldapFilterFactory, serviceAuthLdapProperties.getPersonSearchBase(), serviceAuthLdapProperties.getOuSearchBase());
    }

    /**
     * The LdapTemplate based in the ContextSource
     *
     * @return The LdapTemplate based in the ContextSource
     */
    @Bean
    public LhmLdapClient lhmLdapTemplate(final LdapContextSource ldapContextSource, final LdapQueryFactory ldapQueryFactory) {
        return new LhmLdapClient(this.contextSourceTarget(), ldapQueryFactory);
    }

}
