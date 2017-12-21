package com.demo.redis.config;



import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.demo.redis.service.ConsumerImpl;
import com.demo.redis.service.ConsumerImpl2;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisConfig  extends CachingConfigurerSupport{
	
	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
		//设置缓存过期时间
		rcm.setDefaultExpiration(60);//秒
		return rcm;
	}

	@Bean(name="jedisPoolConfig")
	JedisPoolConfig jedisPoolConfig(
			@Value("${redis.pool.maxTotal}") int maxTotal,
			@Value("${redis.pool.maxIdle}") int maxIdle,
			@Value("${redis.pool.minIdle}") int minIdle,
			@Value("${redis.pool.maxWaitMillis}") int maxWaitMillis,
			@Value("${redis.pool.minEvictableIdleTimeMillis}") long minEvictableIdleTimeMillis,
			@Value("${redis.pool.numTestsPerEvictionRun}") int numTestsPerEvictionRun,
			@Value("${redis.pool.timeBetweenEvictionRunsMillis}") long timeBetweenEvictionRunsMillis
			){
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxTotal(maxTotal);
		jpc.setMaxIdle(maxIdle);
		jpc.setMinIdle(minIdle);
		jpc.setMaxWaitMillis(maxWaitMillis);
		jpc.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		jpc.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		jpc.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		return jpc;
	}
	
	@Bean(name="jedisConnectionFactory")
	JedisConnectionFactory jedisConnectionFactory(
			@Qualifier("jedisPoolConfig") JedisPoolConfig poolConfig,
			@Value("${redis.timeout}") int timeout,
			@Value("${redis.host}") String host,
			@Value("${redis.database}") int database,
			@Value("${redis.port}") int port
			){
		JedisConnectionFactory jcf = new JedisConnectionFactory(poolConfig);
		jcf.setHostName(host);
		jcf.setDatabase(database);
		jcf.setPort(port);
		jcf.setTimeout(timeout);
		jcf.setUsePool(true);
		return jcf;
	}
	
	@Bean(name="redisTemplate")
	RedisTemplate redisTemplate(
			@Qualifier("jedisConnectionFactory") JedisConnectionFactory connectionFactory
			){
		RedisTemplate rt = new RedisTemplate();
		rt.setConnectionFactory(connectionFactory);
		rt.setKeySerializer(new StringRedisSerializer());
		rt.setHashKeySerializer(new StringRedisSerializer());
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
		rt.setValueSerializer(jackson2JsonRedisSerializer);
		rt.setHashValueSerializer(jackson2JsonRedisSerializer);
		rt.setEnableTransactionSupport(true);
		rt.afterPropertiesSet();
		return rt;
	}
	@Bean
    RedisMessageListenerContainer container(
    		@Qualifier("jedisConnectionFactory") JedisConnectionFactory connectionFactory,
    		@Value("${redis.channel}") String channel,
    		//监听方法一
    		@Qualifier("listenerAdapter") MessageListenerAdapter listenerAdapter,
    		//监听方法二
    		@Qualifier("listenerAdapter2") MessageListenerAdapter listenerAdapter2
    		) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapter, new PatternTopic(channel));
//        container.addMessageListener(listenerAdapter2, new PatternTopic(channel));

        return container;
    }
	@Bean(name="listenerAdapter")
    MessageListenerAdapter listenerAdapter(ConsumerImpl consumer) {
        return new MessageListenerAdapter(consumer, "onMessage");
    }
	@Bean(name="listenerAdapter2")
    MessageListenerAdapter listenerAdapter2(ConsumerImpl2 consumer) {
        return new MessageListenerAdapter(consumer, "onMessage");
    }
	
}
