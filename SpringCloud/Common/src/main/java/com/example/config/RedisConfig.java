package com.example.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * redis序列化配置
 * @PackageName: com.example.config
 * @ClassName: redisConfig
 * @Description: redisConfig
 * @Author: Winjay
 * @Date: 2022-04-10 17:12:24
 */
@Configuration
public class RedisConfig {

    /**
     * 自定义缓存管理器
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 创建String和JSON序列化对象，分别对key和value的数据进行类型转换
        RedisSerializer<String> strSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jacksonSeial.setObjectMapper(objectMapper);

        // 自定义缓存数据序列化方式和有效期限
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(10))   // 设置缓存有效期为1天
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(strSerializer)) // 使用strSerializer对key进行数据类型转换
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSeial)) // 使用jacksonSeial对value的数据类型进行转换
                .disableCachingNullValues();   // 对空数据不操作

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
        return cacheManager;
    }

    /**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        /**
         * 配置具体的序列化方式
         */

        //JSON序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
       // om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
       // om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
        /*om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);*/
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的value序列化采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }


    @Bean
    public JdkSerializationRedisSerializer jdkSerializationRedisSerializer() {
        return new JdkSerializationRedisSerializer();
    }

    @PostConstruct
    public void initFastJson(){//fastJson 设置全局安全性问题
        ParserConfig.getGlobalInstance().setSafeMode(true);
    }

}