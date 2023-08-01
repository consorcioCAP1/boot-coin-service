package com.nttdata.bootcamp.bootcoinservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.nttdata.bootcamp.bootcoinservice.dto.ExchangeRateDto;

@Configuration
public class RedisConfig {

	@Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

	@Bean
	public ReactiveRedisTemplate<String, ExchangeRateDto> exchangeRateReactiveRedisTemplate(
	            @Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory factory) {
	    Jackson2JsonRedisSerializer<ExchangeRateDto> jsonRedisSerializer = 
	    		new Jackson2JsonRedisSerializer<>(ExchangeRateDto.class);
	
	    RedisSerializationContext<String, ExchangeRateDto> serializationContext = RedisSerializationContext
	            .<String, ExchangeRateDto>newSerializationContext(new StringRedisSerializer())
	            .value(jsonRedisSerializer)
	            .build();
	
	    return new ReactiveRedisTemplate<>(factory, serializationContext);
	}

}
