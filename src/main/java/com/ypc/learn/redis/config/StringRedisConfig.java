package com.ypc.learn.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * @description:
 * @Author: ypcfly
 * @Date: 19-5-4 下午9:31
 */
@Configuration
public class StringRedisConfig {

	private Integer count = 1;

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

	@Bean
//	@Scope("prototype")
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		LOGGER.info(">>>> custom stringRedisTemplate configuration start, count={} <<<<",count);
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();

		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);

		stringRedisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
		stringRedisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

		count++;
		return stringRedisTemplate;
	}

}
