package org.odyssey.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/*
 * CLASS:	Implementation for IWordSeperator interface. 
 * 
 * NOTE:  
 * 		Store the entire dictionary compulsory in LOWER case.
 * 		WordSeperator uses a dictionary of word as a lookup table and It does not handle any spelling mistakes. 
 * 		Also, some words get precedence over other if they are grouped together in a unique arrangement. 
 * 
 * 	HOW TO USE	?	
 * 		IWordSeperator seperator = new WordSeperator("location of dictionary file");
 *		seperator.segmentData("glued string to be convert") 
 *
 *	@author Abhijeet Shedge
 *	@version 1.0
 *
 */

public class WordSeperator implements IWordSeperator{
	
	private Set<String> dictionary;
	
	// Set the instance variables upon object/instance creation. 
	public WordSeperator(String fileLocation){
		this.dictionary = new HashSet<String>();
		fillDictionary(fileLocation);
	}
	
	/*
	 * Method:	Splits the string and performs formating on the combined strings using available dictionary. 
	 * 
	 * Returns: A formated version of the input string, all glues words are separated by spaces in the resultant string.  
	 * 
	 */
	@Override
	public StringBuffer segmentData(String originalData){
		
		String[] splitData = originalData.split(" ");
		StringBuffer modifiedData = new StringBuffer();
		
		for(int index=0; index<splitData.length; index++){
			modifiedData.append(formatData(splitData[index])+" ");
		}
		
		return modifiedData;
	}
	
	
	
	/*
	 *  Method: formatData -- Formats a piece of string separated by space in the original string.
	 *  		Handles intermediate special characters by separating them out, discards incorrect string.
	 *  
	 *  Return: returns a 'StringBuffer' which is a formated version of the input piece of string.
	 *    
	 */
	private StringBuffer formatData(String data){
		
		StringBuffer tempData = new StringBuffer();
		
		if(dictionary.contains(data)){
			// No grouping of words, return the data/string as it is. 
			tempData.append(data);
		}else{
			// Separate the words, return the formated string. 
			int start=0;
			for(int index=0; index<=data.length(); index++){
				
				// Incoming data is alz compared in lower case. To preserve original formating & is done only during comparison.
				if( dictionary.contains(data.substring(start, index).toLowerCase()) ){	
					
					/*
					 * In the substring(group of words) check if further there is a possibility of obtaining a valid meaningful string. 
					 * If, yes modify currently retrieved string
					 */
					for(int i=(index+1); i<=data.length(); i++){
						if(dictionary.contains(data.substring(start, i).toLowerCase())){
							index=i;
						}
					}
					
					
					tempData.append(data.substring(start, index)+" ");
					start = index;	
									
					
					// Since the remaining String might have valid words, recursively check it and append the data to new string. 
					String remaining = data.substring(index, data.length());
					StringBuffer remain = formatData(remaining);
					if(remain!=null){
						return tempData.append(remain+" ");
					}
					
				// If the string contains anything other than a-z, separate it. 
				}else if( (!(data.substring(start, index)).matches("[A-Za-z]+")) && index!=start ){
					tempData.append(data.charAt(index-1)+" ");
					start=index;  
				}
				
			}	
			
			// if data is short version of original do not add - we don't want loss of information 
			if(tempData.length() < data.length()){
				tempData.setLength(0);
				return (tempData.append(data));
			}
			
			
			return tempData;		
		}
		return tempData;	
		
	}
	
	/*
	 * WordSeperator API will load the intended 'dictionary of words' automatically. 
	 */
	private boolean fillDictionary(String fileLocation){
		
		boolean success = true;
	    try {
	    	BufferedReader bufferReader = new BufferedReader(new FileReader(fileLocation));
	        String line = bufferReader.readLine();

	        while (line != null) {
	            dictionary.add(line.toLowerCase().trim());
	            line = bufferReader.readLine();
	        }
	        bufferReader.close();
	        	        
	    } catch(FileNotFoundException e){
	    	e.printStackTrace();
	    	success = false;	    	
	    } catch(IOException e){
	    	e.printStackTrace();
	    	success = false; 
	    } 
	    
	    return success;
	}
	
	
}
