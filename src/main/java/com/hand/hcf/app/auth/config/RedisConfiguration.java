
package com.hand.hcf.app.auth.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hcf.app.auth.dto.AuthenticationCode;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {

    private final RedisProperties properties;
    private final RedisSentinelConfiguration sentinelConfiguration;

    public RedisConfiguration(RedisProperties properties, ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider) {
        this.properties = properties;
        this.sentinelConfiguration =
            sentinelConfigurationProvider.getIfAvailable();
    }

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        } else {
            RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
            if (sentinelProperties != null) {
                RedisSentinelConfiguration config = new RedisSentinelConfiguration();
                config.master(sentinelProperties.getMaster());
                config.setSentinels(this.createSentinels(sentinelProperties));
                return config;
            } else {
                return null;
            }
        }
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        ArrayList nodes = new ArrayList();
        String[] var3 = (String[]) sentinel.getNodes().toArray();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String node = var3[var5];

            try {
                String[] ex = StringUtils.split(node, ":");
                Assert.state(ex.length == 2, "Must be defined as \'host:port\'");
                nodes.add(new RedisNode(ex[0], Integer.parseInt(ex[1])));
            } catch (RuntimeException var8) {
                throw new IllegalStateException("Invalid redis sentinel property \'" + node + "\'", var8);
            }
        }

        return nodes;
    }


   @Bean(name = "redisTemplate")
   @SuppressWarnings("unchecked")
   @ConditionalOnMissingBean(name = "redisTemplate")
   public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
       RedisTemplate<Object, Object> template = new RedisTemplate<>();

       //使用fastjson序列化  某些值的反序列化会报错
       FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
       // value值的序列化采用fastJsonRedisSerializer
       template.setValueSerializer(fastJsonRedisSerializer);
       template.setHashValueSerializer(fastJsonRedisSerializer);
       // key的序列化采用StringRedisSerializer
       template.setKeySerializer(new StringRedisSerializer());
       template.setHashKeySerializer(new StringRedisSerializer());

       template.setConnectionFactory(redisConnectionFactory);
       return template;
   }

    /**
     * 改用JDK的反序列化
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "redisTemplateForJDK")
    public RedisTemplate<Object, Object> redisTemplateForJDK(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer(new ObjectMapper());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    @Bean
    public RedisTemplate<String, AuthenticationCode> authenticationServiceredisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, AuthenticationCode> redisTemplate =new  RedisTemplate<String, AuthenticationCode>();
     redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}