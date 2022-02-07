package com.payten.credit.config;

import com.payten.credit.repository.credit.redis.CacheCredit;
import com.payten.credit.repository.user.redis.CacheUser;
import com.payten.credit.service.credit.Credit;
import com.payten.credit.service.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean("userRedisTemplate")
    public RedisTemplate<String, CacheUser> userRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String,CacheUser> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }

    @Bean("creditRedisTemplate")
    public RedisTemplate<String, CacheCredit> creditRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String,CacheCredit> template =  new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }


}
