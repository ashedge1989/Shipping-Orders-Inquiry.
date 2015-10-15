package org.odyssey.api;

/*
 * Caching system, implements ICache interface. 
 * 
 * @author  Abhijeet  Shedge
 * @version 1.0
 * 
 */
public class Cache implements ICache{

	// size of cache
	public int cache_size;
	// list of indexing elements/buckets in cache
	public BucketList[] cacheBuckets; 
	
	
	
	/*
	 *		Cache Constructors 
	 */
	
	// default constructor sets default size to the Cache
	public Cache(){
		this.cache_size = 500;
		this.cacheBuckets = new BucketList[cache_size];
	}
	
	// Constructor to create a cache of desirable size
	public Cache(int size){
		this.cache_size = size;
		this.cacheBuckets = new BucketList[cache_size];
	}

	
	
	/*	
	 * Method: generateHashCode	-	A hash function which generates a number between 0 to cache_size. 
	 * 								
	 * Return:	A 'bucketNumber. This number (bucketNumber) helps to point to a unique 'BucketList'
	 * 			which holds the data.'
	 * 
	 * Here,
	 * 		Key - String is an immutable class hence keep String as the key. 
	 * 
	 */
	private int generateHashCode(String key) {
	
		int bucketNumber = (key.hashCode())%(cache_size);	
		
		if(bucketNumber<0){
			// bucketNumber = 0; -- or choose arbitrary
			bucketNumber = cache_size-1;
		}
		
		return bucketNumber;

	}
	
	
	/*
	 * Method:	A Key/String value is used to locate the bucket/slot where key's with similar
	 * 			hash code are stored. Keys with similar hashCode will be then arranged in the form of a linedList.
	 * 
	 * Return:	true -- if caching was successful 
	 * 			false - if the data cannot be cached
	 * 
	 */
	@Override
	public boolean put(String key, Object value){
		
		int bucketLocation = generateHashCode(key);
		boolean success=false;
		// Locate the intended location by mapping the data to a bucket. 
		if(cacheBuckets[bucketLocation] == null){
			BucketList newList = new BucketList();
			cacheBuckets[bucketLocation] = newList;
			// save data into the cache bucket.
			newList.add(key, value);
			success = true;
		}else{
			BucketList existingList = cacheBuckets[bucketLocation];
			existingList.add(key, value);
			success = true;
		}
		
		return success;
	}
	
	
	/*
	 * Method:	This method takes in a String key and finds the bucket depending on it's hash code.
	 * 			Then with in the List, check each element one by one for the match. 
	 * 
	 * Return:	The intended object matching key, otherwise null. 
	 * 
	 */	
	@Override
	public Object get(String key){
		
		int bucketLocation = generateHashCode(key);
		Object foundValue = null;
		
		if(cacheBuckets[bucketLocation] == null){
			foundValue = null;
		}else{
			BucketList existingList = cacheBuckets[bucketLocation];
			
			// iterate through the bucket elements to find the right object
			int index=1;
			while(index <= existingList.getSize()){				
				DataNode searchnode = (DataNode) existingList.get(index);
				if((searchnode.getKey()).equals(key)){
					foundValue = searchnode.getVal();
					break;
				}		
				index++;
			}						
		}		
		
		return foundValue;
	}

	
	/*
	 * Method:	Accepts the key representing the data to be removed/deleted from cache. 
	 * 
	 * Returns: The data/object that is being removed, otherwise null.
	 *  
	 */
	@Override
	public Object remove(String key) {

		int bucketLocation = generateHashCode(key);
		Object foundValue = null;
		
		if(cacheBuckets[bucketLocation] == null){
			foundValue = null;
		}else{
			BucketList existingList = cacheBuckets[bucketLocation];
			
			// iterate through the bucket elements to find the right object
			int index=1;
			while(index <= existingList.getSize()){				
				DataNode searchnode = (DataNode) existingList.get(index);
				if((searchnode.getKey()).equals(key)){
					foundValue = searchnode.getVal();
					// remove data from the bucket  
					existingList.remove(index);								
					break;
				}		
				index++;
			}						
		}		
		
		return foundValue;
	}
	
	
	
}