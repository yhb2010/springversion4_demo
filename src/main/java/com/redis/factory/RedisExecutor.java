package com.redis.factory;

import redis.clients.jedis.ShardedJedis;

public abstract class RedisExecutor<T> {

    /**
     * 回调
     *
     * @return 执行结果
     */
    abstract T execute(ShardedJedis jedis, String appName);

    /**
     * 调用{@link #execute(ShardedJedis jedis)}并返回执行结果 它保证在执行{@link #execute(ShardedJedis jedis)}
     * 之后释放数据源
     *
     * @return 执行结果
     */
    public T getResult(RedisPool redisPool, String appName) {
        T result = null;
        ShardedJedis jedis = null;
        try {
            jedis = redisPool.getResource();
            result = execute(jedis, appName);
        } catch (Throwable e) {
            throw new RuntimeException("Redis execute exception", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }
}
