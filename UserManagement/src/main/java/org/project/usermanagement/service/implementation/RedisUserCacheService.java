package org.project.usermanagement.service.implementation;

import org.project.usermanagement.entity.User;
import org.project.usermanagement.service.UserCacheService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisUserCacheService implements UserCacheService {
    private final StringRedisTemplate redisTemplate;

    public RedisUserCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void cacheUserFields(User user) {
        this.redisTemplate.opsForSet().add("usernames", user.getUserName());
        this.redisTemplate.opsForSet().add("emails", user.getEmail());
        this.redisTemplate.opsForSet().add("phone", user.getPhone());
    }
}
