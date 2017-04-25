package com.april.fourth.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Created by daipengfei
 * on 2017/4/25.
 */
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        net.sf.ehcache.config.Configuration configuration =
                new net.sf.ehcache.config.Configuration();
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("weather");
        cacheConfiguration.setMaxEntriesLocalHeap(5000);
        cacheConfiguration.setTimeToIdleSeconds(0);
        cacheConfiguration.setTimeToLiveSeconds(0);
        configuration.addCache(cacheConfiguration);
        return net.sf.ehcache.CacheManager.newInstance(configuration);
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

}
