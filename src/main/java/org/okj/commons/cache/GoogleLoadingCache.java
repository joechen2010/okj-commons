package org.okj.commons.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

public class GoogleLoadingCache extends GoogleCache{
	
	CacheLoader<String, Object> loader = new CacheLoader<String, Object>() {
        public Object load(String key) {
        	for(CacheAdapter adapter : caches){
        		if(key.equals(adapter.getKey()))
        			return adapter.load(key);
        	}
        	return null;
        }
    };
    
    @Override
    public void setCache(Cache<String, Object> cache) {
		this.cache =  CacheBuilder.newBuilder()
	            .maximumSize(size)
	            .weakKeys()
	            .softValues()
	            .refreshAfterWrite(refreshTime, TimeUnit.SECONDS)
	            .expireAfterWrite(expireTime, TimeUnit.SECONDS)
	            // .removalListener(RemovalListeners.asynchronous(listener,executor))
	            // .removalListener(MY_LISTENER)
	            .build(loader);
	}

}
