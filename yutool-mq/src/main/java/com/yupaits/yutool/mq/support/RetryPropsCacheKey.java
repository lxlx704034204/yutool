package com.yupaits.yutool.mq.support;

import com.yupaits.yutool.cache.support.CacheKeyGenerator;
import lombok.AllArgsConstructor;

/**
 * 重试配置缓存key
 * @author yupaits
 * @date 2019/7/18
 */
@AllArgsConstructor
public class RetryPropsCacheKey implements CacheKeyGenerator {
    private static final String PROPS_CACHE_KEY_PREFIX = "mq:retry:props:";

    /**
     * 关联消息ID
     */
    private String correlationId;

    @Override
    public String cacheKey() {
        return PROPS_CACHE_KEY_PREFIX + correlationId;
    }
}
