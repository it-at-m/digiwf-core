package de.muenchen.oss.digiwf.adapter.out.ldap;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Profile("groups-ldap")
public class LdapCacheConfiguration {
    static final String USER_GROUPS_CACHE = "userGroupsCache";
    static final String USER_CACHE = "userCache";
    static final String GROUPS_MEMBERS = "groupsMembersCache";
    private static final int LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE = 60 * 15;

    @Bean
    public Cache userGroupsCache(final Ticker ticker) {
        return new CaffeineCache(USER_GROUPS_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }

    @Bean
    public Cache userCache(final Ticker ticker) {
        return new CaffeineCache(USER_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }

    @Bean
    public Cache groupsMembersCache(final Ticker ticker) {
        return new CaffeineCache(GROUPS_MEMBERS,
                Caffeine.newBuilder()
                        .expireAfterWrite(LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }
}
