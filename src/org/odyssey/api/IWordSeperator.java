package org.odyssey.api;

/*
 * @author Abhijeet Shedge
 * @version 1.0
 */
public interface IWordSeperator {
	
	// Use this method to separated the glued words.  
	public abstract StringBuffer segmentData(String originalData);
	
}
