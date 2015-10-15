package org.odyssey.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement;
import org.odyssey.config.ConfigOdysseyTransportation;
import org.odyssey.model.Line;
import org.odyssey.model.Location;
import org.odyssey.model.Order;

public class LookUpOrderDAO extends Thread {

	private int orderId = -1;
    private Connection connection;
    private DelegatingPreparedStatement statement;
    private Order retrievedOrder;    
		
	public LookUpOrderDAO(int orderId){
		this.orderId = orderId;
	}
	

	@Override
	public void run() {
		super.run();
	
		Order order = getOrder(orderId);

		synchronized(this){
			this.retrievedOrder = order;
			this.notifyAll();
		}		
		
	}
	
	

	private Order getOrder(int orderId){
		
		Order newOrder = new Order();
		
		// Get DataSource
		try {
		    connection = (ConfigOdysseyTransportation.dataSource).getConnection();
            
		    String query = "SELECT * FROM instruction WHERE orderId = ? ";
		    statement = (DelegatingPreparedStatement) connection.prepareStatement(query);
		    statement.setInt(1, orderId);
		    ResultSet r = statement.executeQuery();
		    
		    System.out.println("inst:"+r);
		    // get instructions
		    while(r.next()){
		    	newOrder.setInstructions(r.getString(2));
		    }
		    
		    
		    query = "SELECT * FROM locations WHERE orderId = ? ";
		    statement = (DelegatingPreparedStatement) connection.prepareStatement(query);
		    statement.setInt(1, orderId);
		    r = statement.executeQuery();
		    // get locations
		    while(r.next()){
		    	// set order id
		    	newOrder.setOrderId(r.getInt(1));
		    	
		    	Location to = new Location();    
		    	to.setZip(r.getString(2));
		    	to.setCity(r.getString(3));
		    	to.setState(r.getString(4));
		    	newOrder.setTo(to);
		    	
		    	Location from = new Location();    
		    	from.setZip(r.getString(5));
		    	from.setCity(r.getString(6));
		    	from.setState(r.getString(7));
		    	newOrder.setFrom(from);
		    }
		    
		    query = "SELECT * FROM lineInfo WHERE orderId = ? ";
		    statement = (DelegatingPreparedStatement) connection.prepareStatement(query);
		    statement.setInt(1, orderId);
		    r = statement.executeQuery();
		    List<Line> lines = new ArrayList<Line>();	    
		    while(r.next()){
		    	Line line = new Line();
		    	if((r.getString(2)).equals("Y")){
		    		line.setHazard(true);
		    	}else{
		    		line.setHazard(false);
		    	}
		    	line.setProduct(r.getString(3));
		    	line.setVolume(r.getDouble(4));
		    	line.setWeight(r.getDouble(5));
		    	lines.add(line);
		    }
		    newOrder.setLines(lines);
		    
            			
		} catch (SQLException e) {
			e.printStackTrace();
			
			//error in inserting data -- SQLTimeoutException or SQLException thrown.
			
		} 
		
		
		return (newOrder);
	}


	public Order getRetrievedOrder() {
		return retrievedOrder;
	}
	
	
}
