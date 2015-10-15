package org.odyssey.service;

import org.odyssey.api.ICache;
import org.odyssey.config.ConfigOdysseyTransportation;
import org.odyssey.dao.LookUpOrderDAO;
import org.odyssey.model.Order;

public class LookUpOrderService {

	public Order getOrderById(int orderId){
		
			
		
		LookUpOrderDAO lookUpOrderDAO = new LookUpOrderDAO(orderId);
	    (ConfigOdysseyTransportation.executor).execute(lookUpOrderDAO);
	    
	    synchronized(lookUpOrderDAO){
	    	try {
	    		lookUpOrderDAO.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }
	    
		
		
		return (lookUpOrderDAO.getRetrievedOrder());
	}
	
	
}
