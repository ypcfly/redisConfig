package com.ypc.learn.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @description:
 * @Author: ypcfly
 * @Date: 19-4-30 下午9:57
 */
@Configuration
public class RedisConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

	/**
	 *  自定义cache manager  这个配置主要配合spring cache使用，和使用redisTemplate操作没有关系
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		LOGGER.info(">>>> custom redis cache manager start <<<<");
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

		// 指定key value的序列化类型
		RedisSerializer<Object> jsonSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		RedisSerializationContext.SerializationPair<Object> jsonSerializationPair = RedisSerializationContext.SerializationPair
				.fromSerializer(jsonSerializer);
		RedisSerializationContext.SerializationPair<String> stringSerializationPair = RedisSerializationContext.SerializationPair
				.fromSerializer(RedisSerializer.string());

		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(stringSerializationPair)
														.serializeValuesWith(jsonSerializationPair)
				                                        .disableCachingNullValues().entryTtl(Duration.ofSeconds(1000));

		RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);

		return redisCacheManager;
	}

	/**
	 * 自定义redisTemplate序列化策略，注入redisTemplate时会使用该方法返回的bean
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		LOGGER.info(">>>> custom redisTemplate configuration start <<<<");
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		// 配置jackson序列化策略默认
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);

		// 配置其他的序列化策略
		redisTemplate.setKeySerializer(RedisSerializer.string());
		redisTemplate.setHashKeySerializer(RedisSerializer.string());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//		redisTemplate.setHashValueSerializer(RedisSerializer.json());

		return redisTemplate;
	}

}
