package model.dao;

import java.io.IOException;

import play.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCache {
	private static JedisPool jedisPool;
	private static RedisCache instance = null;

	private RedisCache() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		String redisCacheUrl = play.Play.application().configuration().getString("redisCacheUrl");
		Integer redisCachePort = play.Play.application().configuration().getInt("redisCachePort");
		
		jedisPool = new JedisPool(jedisPoolConfig, redisCacheUrl, redisCachePort, 10000);
		
		cleanCache();
	}

	public void cleanCache() {
		Logger.info("M4CE.DataFeed.RedisCache >> Flushing DB");
		Jedis jedis = jedisPool.getResource();
		jedis.flushDB();
		jedisPool.returnResource(jedis);
	}

	public Object getFromCache(String key) {
		Jedis jedis = jedisPool.getResource();
		Object object = jedis.get(key);
		jedisPool.returnResource(jedis);
		
		return object;
	}

	public boolean existsOnCache(String key) {
		Jedis jedis = jedisPool.getResource();
		Boolean exists = jedis.exists(key);
		jedisPool.returnResource(jedis);
		
		return exists;
	}

	public void putOnCache(String key, String value, boolean expires)
			throws IOException {
		Jedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		
		if (expires) {
			jedis.expire(key, 60 * 1); // 1 minute
		}
		
		jedisPool.returnResource(jedis);
	}

	public void putOnCacheWithExpirationTime(String key, String value,
			Integer expirationTimeInMinutes) {
		Jedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		jedis.expire(key, expirationTimeInMinutes * 60); // convert second to minute
		jedisPool.returnResource(jedis);
	}
	
	public static RedisCache getInstance() {
		Logger.info("RedisCache >> Getting the instance");
        synchronized (RedisCache.class) {
            try {
                if (instance == null) {
                	Logger.info("RedisCache >> New instance of RedisCache");
                    instance = new RedisCache();
                }

                return instance;
            } finally {
            }
        }
    }
}