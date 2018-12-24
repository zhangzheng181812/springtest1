package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by admin on 2017/12/5.
 */
@Configuration
public class RedisCacheConfig {


    /**
     * 指定spring-data-redis 的序列化规则。
     * 备注：name需要重新指定，不然CacheSerice自动注入的就不是这个配置的RedisTemplate 而是默认的。
     * @param factory
     * @return
     */
    @Bean(name="stringRedisTemplate")
    public RedisTemplate getRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(factory);
        //指定序列化规则，key value全部使用String类型与jedis保持一致（其他项目使用的）
        //不指定时，则默认使用jdk的序列化规则，key或者value会含有\xAC\xED\x00\x05t\x00这样的字符串。
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer );
        template.setValueSerializer(stringSerializer );
        template.setHashKeySerializer(stringSerializer );
        template.setHashValueSerializer(stringSerializer );
        template.afterPropertiesSet();
        return template;
    }


}
