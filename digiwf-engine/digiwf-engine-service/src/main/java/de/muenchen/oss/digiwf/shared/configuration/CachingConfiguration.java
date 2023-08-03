/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.shared.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import de.muenchen.oss.digiwf.legacy.user.external.client.LhmLdapClient;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * This class provides the caches.
 * To disable the caching functionality delete this class, remove the corresponding bean creation methods
 * or remove the annotation {@link EnableCaching} above the class definition.
 */
@Configuration
@EnableCaching
public class CachingConfiguration {

    private static final int LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE = 28800;

    /**
     * Creates a bean to get a time source.
     *
     * @return The time source.
     */
    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

    /**
     * The config to provide a cache for ldap template {@link LhmLdapClient}.
     *
     * @param ticker The time source for the cache.
     * @return The cache.
     */
    @Bean
    public Cache ldapUsersCache(final Ticker ticker) {
        return new CaffeineCache(LhmLdapClient.CACHE_USERS,
                Caffeine.newBuilder()
                        .expireAfterWrite(LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }

    /**
     * The config to provide a cache for ldap template {@link LhmLdapClient}.
     *
     * @param ticker The time source for the cache.
     * @return The cache.
     */
    @Bean
    public Cache ldapUsersByNameCache(final Ticker ticker) {
        return new CaffeineCache(LhmLdapClient.CACHE_USERBYNAME,
                Caffeine.newBuilder()
                        .expireAfterWrite(LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }

    /**
     * The config to provide a cache for ldap template {@link LhmLdapClient}.
     *
     * @param ticker The time source for the cache.
     * @return The cache.
     */
    @Bean
    public Cache ldapOusCache(final Ticker ticker) {
        return new CaffeineCache(LhmLdapClient.CACHE_OUS,
                Caffeine.newBuilder()
                        .expireAfterWrite(LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }

    /**
     * The config to provide a cache for ldap template {@link LhmLdapClient}.
     *
     * @param ticker The time source for the cache.
     * @return The cache.
     */
    @Bean
    public Cache ldapOuTreeCache(final Ticker ticker) {
        return new CaffeineCache(LhmLdapClient.CACHE_OUTREE,
                Caffeine.newBuilder()
                        .expireAfterWrite(LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }

}
