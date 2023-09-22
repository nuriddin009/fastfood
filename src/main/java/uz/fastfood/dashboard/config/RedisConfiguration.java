package uz.fastfood.dashboard.config;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.fastfood.dashboard.entity.Category;
import uz.fastfood.dashboard.entity.Product;

@Configuration
@EnableCaching
public class RedisConfiguration {
    private javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, Category.class.getName());
            createCache(cm, Product.class.getName());
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
