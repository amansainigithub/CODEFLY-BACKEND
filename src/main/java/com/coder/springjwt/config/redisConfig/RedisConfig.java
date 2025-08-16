//package com.coder.springjwt.config.redisConfig;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        // Using String serializers for key and value
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new StringRedisSerializer());
//
//        return template;
//    }
//
//
////    private RedisServer redisServer;
////
////    @PostConstruct
////    public void startRedis() throws IOException {
////        redisServer = new RedisServer(6379);  // default Redis port
////        redisServer.start();
////    }
////
////    @PreDestroy
////    public void stopRedis() {
////        if (redisServer != null) {
////            redisServer.stop();
////        }
////    }
////
////    @Bean
////    public RedisServer redisServer() {
////        return redisServer;
////    }
//}
