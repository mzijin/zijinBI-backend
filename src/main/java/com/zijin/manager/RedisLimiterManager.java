package com.zijin.manager;

import com.zijin.common.ErrorCode;
import com.zijin.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 专门提供 RedisLimiter 限流基础服务的（提供了通用的能力）
 */

@Service
public class RedisLimiterManager {
@Resource
       private RedissonClient redissonClient;
    /**
     * 限流操作
     *
     * @param key 区分不同的限流器，比如不同的用户 id 应该分别统计
     */
    public void doRateLimit(String key){
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL,2,1,RateIntervalUnit.SECONDS);
        boolean canOp = rateLimiter.tryAcquire(1);
        if (!canOp){
            throw  new BusinessException(ErrorCode.TOO_MANY_REQUEST);

        }
    }
}
