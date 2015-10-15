package org.odyssey.api;

import org.odyssey.model.Order;

public class TestCache {

	
	
	public static void main(String[] args) {
	
		Order o = new Order();
		o.setOrderId(100);
		Order o1 = new Order();
		o1.setOrderId(200);
		
		String key = String.valueOf(o.getOrderId());
		
		ICache c = new Cache();
		c.put(key, o);
		c.put("abc", o1);
		
		System.out.println("Saved: "+((Order)c.get("abc")).getOrderId());
		System.out.println("Saved: "+((Order)c.get(key)).getOrderId());
		
		
	}
	
	
}
