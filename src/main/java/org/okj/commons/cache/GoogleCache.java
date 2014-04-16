package org.okj.commons.cache;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.cache.Cache;
import com.google.common.collect.Lists;

public abstract class GoogleCache {
	
	@Qualifier("caches")
	protected List<CacheAdapter> caches = Lists.newArrayList();
	protected Integer size = 10000;
	protected Integer refreshTime = 120;
	protected Integer expireTime = 6000;
	
	protected Cache<String , Object> cache = null;
    
    public void invalidate(String key) {
        cache.invalidate(key);
    }
    
    @SuppressWarnings("deprecation")
	public Object get(String key) {
    	try {
			return cache.get(key);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

	public List<CacheAdapter> getCaches() {
		return caches;
	}

	public void setCaches(List<CacheAdapter> caches) {
		this.caches = caches;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Integer refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

	public Cache<String, Object> getCache() {
		return cache;
	}

	public void setCache(Cache<String, Object> cache) {
		this.cache = cache;
	}
	
	public Cache<String, Object> setCache(Callable<Object> callable) {
		return cache;
	}
    
}
