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
    private static final int LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE = 60 * 5;

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
     * The config to provide a cache for {@link LdapAdapter}.
     *
     * @param ticker The time source for the cache.
     * @return The cache.
     */
    @Bean
    public Cache groupCache(final Ticker ticker) {
        return new CaffeineCache(LdapAdapter.GROUP_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(LDAP_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }
}
