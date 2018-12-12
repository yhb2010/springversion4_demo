package com.redis.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class RedisPoolFactory {
	
    /**
     * 初始化RedisPool
     */
    public RedisPool initialPool(String servers, String masters, String password, int database, int maxTotal, int maxIdle, int minIdle, int timeout) {
        Set<String> sentinels = new HashSet<String>();
        if (servers != null)
            sentinels.addAll(Arrays.asList(servers.split(" ")));
        List<String> masterList = new ArrayList<String>();
        if (masters != null)
            masterList.addAll(Arrays.asList(masters.split(" ")));
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);

        return new RedisPool(masterList, sentinels, poolConfig, timeout, password, database);
    }

}
