package org.okj.commons.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GoogleCalledCache extends GoogleCache{
	
  
    Cache<String , Object> callableCache(final String key) throws Exception{
        return setCache(new Callable<Object>(){
              @Override
              public Object call() throws Exception {
            	  for(CacheAdapter adapter : caches){
              		if(key.equals(adapter.getKey()))
              			return adapter.load(key);
              	}
            	  return null;
              }
        });
    }
     
     @Override
     public Cache<String , Object> setCache(Callable<Object> callable){
            cache = CacheBuilder.newBuilder()
                        .maximumSize(10000)
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        // .removalListener(RemovalListeners.asynchronous(listener,executor))
                        // .removalListener(MY_LISTENER)
                        // .weakKeys()
                        // .weakValues()
                        .build();
            return cache;
      }

}
