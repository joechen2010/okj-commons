package org.okj.commons.cache;

import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;

public class SpringCacheAdapter extends ConcurrentMapCacheFactoryBean{
	
	public String getName(){
		return this.getName();
	}
	
}
