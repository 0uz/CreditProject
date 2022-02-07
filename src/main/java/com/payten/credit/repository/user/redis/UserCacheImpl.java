package com.payten.credit.repository.user.redis;

import com.payten.credit.service.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCacheImpl implements UserCache{

    private final RedisTemplate<String,CacheUser> userRedisTemplate;

    @Override
    public Optional<User> retrieveUser(Long userId) {
        CacheUser cacheUser = userRedisTemplate.opsForValue().get("payten:user:" + userId);
        return Optional.ofNullable(cacheUser).map(CacheUser::toModel);
    }

    @Override
    public void createUser(User user) {
        CacheUser cacheUser = CacheUser.from(user);
        userRedisTemplate.opsForValue().set("payten:user:"+user.getId(),cacheUser, Duration.ofSeconds(30));
    }
}
