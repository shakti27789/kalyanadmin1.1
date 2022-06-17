package com.exchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;


@Configuration
@EnableRedisRepositories
public class RediesConfig {
	@Bean
	public JedisConnectionFactory connectionFactory() {
		
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName("redis-14463.c98.us-east-1-4.ec2.cloud.redislabs.com");
		configuration.setPort(14463);
		configuration.setPassword("KOh1VstgXoyA1MnqYvEkMbclJuFYC0u5");
		
		return new JedisConnectionFactory(configuration);
		
		
		
		}
		@Bean
		@Primary
		public RedisTemplate<String, Object> redisTemplate() {
		    final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		    template.setConnectionFactory(connectionFactory());
		    template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		    template.setEnableTransactionSupport(false);
		    return template;
		}
}
