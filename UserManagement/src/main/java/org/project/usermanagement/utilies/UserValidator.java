package org.project.usermanagement.utilies;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final StringRedisTemplate redisTemplate;

    public UserValidator(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isUsernameExist(String username) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("usernames", username));
    }

    public boolean isEmailDuplicate(String email) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("emails", email));
    }

    public boolean isPhoneDuplicate(String phone) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("phones", phone));
    }
}
