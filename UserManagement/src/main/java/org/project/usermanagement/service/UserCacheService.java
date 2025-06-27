package org.project.usermanagement.service;

import org.project.usermanagement.entity.User;
import org.springframework.data.redis.core.StringRedisTemplate;

public interface UserCacheService {
    void cacheUserFields(User user);
}
