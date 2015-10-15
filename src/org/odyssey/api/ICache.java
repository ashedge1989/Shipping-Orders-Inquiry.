package org.odyssey.api;

public interface ICache {

	public abstract boolean put(String key, Object value);
	public abstract Object get(String key);
	public abstract Object remove(String key);
	
}



