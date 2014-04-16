package org.okj.commons.cache;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class SpringCacheManager extends SimpleCacheManager{
	
	@Qualifier("springCacheAdapters")
	private Set<SpringCacheAdapter> adapters = Sets.newHashSet();
	@SuppressWarnings("rawtypes")
	private List caches = Lists.newArrayList();
	
	@SuppressWarnings({ "unchecked"})
	public SpringCacheManager(){
		for(SpringCacheAdapter adapter : adapters){
			caches.add(adapter);
		}
		setCaches(caches);
	}
	
	
	public Object get(String cacheName, String key){
		SimpleValueWrapper simplevaluewrapper = (SimpleValueWrapper) this.getCache(cacheName).get(key);
		if(simplevaluewrapper != null)
			return simplevaluewrapper.get();
		return null;
	}

	public void put(String cacheName, String key, Object object){
		getCache(cacheName).put(key, object);
	}
	
	public void clear(String cacheName, String key){
		getCache(cacheName).evict(key);
	}
}
