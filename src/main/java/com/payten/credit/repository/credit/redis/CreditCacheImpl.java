package com.payten.credit.repository.credit.redis;

import com.payten.credit.service.credit.Credit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditCacheImpl implements CreditCache{

    private final RedisTemplate<String,CacheCredit> creditRedisTemplate;

    @Override
    public Optional<Credit> retrieveCredit(Long creditId) {
        CacheCredit cacheCredit = creditRedisTemplate.opsForValue().get("payten:credit:" + creditId);
        return Optional.ofNullable(cacheCredit).map(CacheCredit::toModel);

    }

    @Override
    public void createCredit(Credit credit,Long identificationNo) {
        CacheCredit cacheCredit = CacheCredit.from(credit);
        creditRedisTemplate.opsForValue().set("payten:credit:"+identificationNo,cacheCredit, Duration.ofSeconds(30));
    }
}
