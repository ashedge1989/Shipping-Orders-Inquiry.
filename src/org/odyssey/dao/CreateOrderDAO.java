package org.odyssey.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.odyssey.config.ConfigOdysseyTransportation;
import org.odyssey.model.Order;

import org.apache.tomcat.dbcp.dbcp.DelegatingPreparedStatement;


public class CreateOrderDAO extends Thread{
	
    private Connection connection;
    private DelegatingPreparedStatement statement;
    private Order newOrder;
    private int savedOrderNumber;
    
    public CreateOrderDAO(Order newOrder){
    	this.newOrder = newOrder;
    }
    

	@Override
	public void run() {
		super.run();
		
		int newOrderNumber = saveOrder(newOrder);
		
		// Multiple such threads might update this share variable so allow only one to process at a time. 
		synchronized(this){
			this.savedOrderNumber = newOrderNumber;
			this.notifyAll();
		}
		
		
	}
	    
    
    
	public int saveOrder(Order newOrder){
		
		int success = -1;
		
		// Get DataSource
		try {
		    connection = (ConfigOdysseyTransportation.dataSource).getConnection();
            
		    String query = "INSERT INTO locations (toZip,toCity,toState,fromZip,fromCity,fromState) VALUES (?,?,?,?,?,?)";
		    statement = (DelegatingPreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		    statement.setString(1, newOrder.getTo().getZip());
		    statement.setString(2, newOrder.getTo().getCity());
		    statement.setString(3, newOrder.getTo().getState());
		    statement.setString(4, newOrder.getFrom().getZip());
		    statement.setString(5, newOrder.getFrom().getCity());
		    statement.setString(6, newOrder.getFrom().getState());
		    
		    int rowsAffected = statement.executeUpdate();
		        
		    
		    if(rowsAffected>0){
		    	
		    	try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                System.out.println("Generated key: "+generatedKeys.getLong(1));
		                success = (int) generatedKeys.getLong(1);
		                
		                
		                query = "INSERT INTO instruction VALUES (?,?)";
		                statement = (DelegatingPreparedStatement) connection.prepareStatement(query);
		                statement.setInt(1, success);
		                
		                // before saving the instructions, remove the glued objects
		                StringBuffer strBuff = ConfigOdysseyTransportation.wordSeperator.segmentData(newOrder.getInstructions());
		                statement.setString(2, strBuff.toString());
		                statement.executeUpdate();
		                
		                		                
		                for(int i=0; i<newOrder.getLines().size(); i++){
		                	String setFlag="N";
		                	
		                	query = "INSERT INTO lineInfo VALUES (?,?,?,?,?)";
			                statement = (DelegatingPreparedStatement) connection.prepareStatement(query);
			                if (newOrder.getLines().get(i).isHazard()){
			                	setFlag="Y";
			                }
			                statement.setInt(1, success);
			                statement.setString(2, setFlag);
			                statement.setString(3, newOrder.getLines().get(i).getProduct());
			                statement.setDouble(4, newOrder.getLines().get(i).getVolume());
			                statement.setDouble(5, newOrder.getLines().get(i).getWeight());
			                statement.executeUpdate();
		                }
		                		                		                
		            }
		        }
		    	
		    }else{
		    	success = -1;
		    }
            
            
           
            			
		} catch (SQLException e) {
			e.printStackTrace();
			
			//error in inserting data -- SQLTimeoutException or SQLException thrown.
			return -1; 
		} 
		
				
		return success;
	}


	public int getSavedOrderNumber() {
		return savedOrderNumber;
	}

}
