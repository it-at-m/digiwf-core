package de.muenchen.oss.digiwf.adapter.out.engine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class EngineCacheConfiguration {
    static final String OPTIMIZE_AUTH_CACHE = "optimizeAuthCache";
    private static final int ENGINE_CACHE_ENTRY_SECONDS_TO_EXPIRE = 60 * 15;

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

    @Bean
    public Cache optimizeAuthCache(final Ticker ticker) {
        return new CaffeineCache(OPTIMIZE_AUTH_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(ENGINE_CACHE_ENTRY_SECONDS_TO_EXPIRE, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }
}
