package org.odyssey.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.odyssey.api.IParser;
import org.odyssey.api.ParseXML;
import org.odyssey.config.ConfigOdysseyTransportation;
import org.odyssey.dao.CreateOrderDAO;
import org.odyssey.model.Order;

import com.google.gson.Gson;

public class CreateOrderService {

	
	public int saveOrder(String xmlFileLocation){

		InputStream is = null;
		Order userNewOrder = null;
		
		
				
		// Parse the XML file and create 'Order' object. 
	    try {
	        
	    	is = new FileInputStream(xmlFileLocation);
	        
	    	IParser parse = new ParseXML();		// Parse a new order.	
	    	userNewOrder = parse.parseXML(is);	// Create new Order object.    	
	    		    	
	    	is.close();	    // Close the input stream. 
	        
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
			e.printStackTrace();
		}		
	    
	    	
	    // save the created 'Order' object to database. 
	    CreateOrderDAO createDao = new CreateOrderDAO(userNewOrder);
	    (ConfigOdysseyTransportation.executor).execute(createDao);
	    
	    synchronized(createDao){
	    	try {
				createDao.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    
	    	    
		System.out.println("Returned value: "+createDao.getSavedOrderNumber());

		// If the save operation was successful then save the retrieved data in to CACHE. 
		if(createDao.getSavedOrderNumber() != -1){
			ConfigOdysseyTransportation.orderCache.put(String.valueOf(createDao.getSavedOrderNumber()), userNewOrder);
		}
		
	    return (createDao.getSavedOrderNumber());
	}
	
	
}
