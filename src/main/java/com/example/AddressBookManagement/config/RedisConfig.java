package com.example.AddressBookManagement.config;

import com.example.AddressBookManagement.model.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Address> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Address> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // ✅ Configure ObjectMapper without using deprecated methods
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())  // Support for Java 8 Date/Time API
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        Jackson2JsonRedisSerializer<Address> serializer = new Jackson2JsonRedisSerializer<>(objectMapper, Address.class);

        // ✅ Use String serializer for keys and JSON serializer for values
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}
