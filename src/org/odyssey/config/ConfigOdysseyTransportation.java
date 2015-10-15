package org.odyssey.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.odyssey.api.Cache;
import org.odyssey.api.ICache;
import org.odyssey.api.IWordSeperator;
import org.odyssey.api.WordSeperator;


public class ConfigOdysseyTransportation implements ServletContextListener {

	
	public static ICache orderCache = null;
	public static IWordSeperator wordSeperator = null;
	public static DataSource dataSource = null;
	public static int orderNumber = -1;
	public static ExecutorService executor = null;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
			// do nothing		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		
		// Configure a Cache & create an instance of 'Cache' with default size (500 buckets).
		// for change in total bucket's pass argument to the constructor.
		ConfigOdysseyTransportation.orderCache = new Cache();
		
		
		
		ServletContext context = arg0.getServletContext();
		if( context.getInitParameter("dictionaryFileName")!=null ){
			ConfigOdysseyTransportation.wordSeperator = new WordSeperator(context.getRealPath("/WEB-INF/"+context.getInitParameter("dictionaryFileName")));
		}
		
		
		//configure DB
		try {
		
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
	        ConfigOdysseyTransportation.dataSource = (DataSource)envContext.lookup("jdbc/connectionConfig");
		
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		
		// Fixed size thread pool of 10 worker threads is created. If there are more than 10 requests, the requests are processed as an when
		// the thread becomes available.
		ConfigOdysseyTransportation.executor = Executors.newFixedThreadPool(10);
        
				
	}

}




