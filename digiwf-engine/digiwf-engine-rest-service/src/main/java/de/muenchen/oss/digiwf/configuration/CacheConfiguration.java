package de.muenchen.oss.digiwf.configuration;

import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {
    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }
}
