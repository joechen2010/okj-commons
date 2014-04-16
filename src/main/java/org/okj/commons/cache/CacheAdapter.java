package org.okj.commons.cache;

public abstract class CacheAdapter {
	
	protected String key = CacheAdapter.class.getSimpleName();
	
	protected abstract Object load(String key);

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
