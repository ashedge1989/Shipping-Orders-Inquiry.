package org.odyssey.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.odyssey.model.Line;
import org.odyssey.model.Location;
import org.odyssey.model.Order;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/*
 *	Loads the data from XML file and parses it using XMLPullParser and 
 *	finally converts the raw data into an Object.      
 * 
 * @author  Abhijeet  Shedge
 * @version 1.0
 * 
 */
public class ParseXML implements IParser{

	private Order transportOrder;
	private List<Line> lines = new ArrayList<Line>();
	
	
	@Override
	public Order parseXML(InputStream inputStream) throws XmlPullParserException, IOException{
		
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		parser.setInput(inputStream, "UTF-8"); // XmlPullParserException
		
		int event = parser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
			
			switch(event){
				case XmlPullParser.START_TAG:
					
					if(parser.getName().equals("order")){
						transportOrder = new Order();
						
					} else if(parser.getName().equals("from")){
						
						Location fromLoc = new Location();
						fromLoc.setZip(parser.getAttributeValue(null, "zip"));
						fromLoc.setState(parser.getAttributeValue(null, "state"));
						fromLoc.setCity(parser.getAttributeValue(null, "city"));
						transportOrder.setFrom(fromLoc);
						parser.nextText().trim();
											
					} else if(parser.getName().equals("to")){
						System.out.println("inside to ");
						Location toLoc = new Location();
						toLoc.setZip(parser.getAttributeValue(null, "zip"));
						toLoc.setState(parser.getAttributeValue(null, "state"));
						toLoc.setCity(parser.getAttributeValue(null, "city"));						
						transportOrder.setTo(toLoc);
						parser.nextText().trim();
												
					} else if(parser.getName().equals("lines")){									
						readLine(parser);
						
					} else if(parser.getName().equals("instructions")){						
						transportOrder.setInstructions(parser.nextText().trim());
					}					
					break;
					
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("order")){
						return transportOrder;
					}
					break;
					
			}
			event = parser.next();	// IOException		
		}

		 
		 
		 
		return transportOrder;
	}
	
	
	// parses the multiple 'line' inside 'lines' 
	private void readLine(XmlPullParser parser) throws XmlPullParserException, IOException {
	    
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        
	        if ((parser.getName()).equals("line")) {	            
	            Line line = new Line();
				line.setHazard(Boolean.parseBoolean(parser.getAttributeValue(null, "hazard")));
				line.setProduct(parser.getAttributeValue(null, "product"));
				line.setVolume((Double.parseDouble((parser.getAttributeValue(null, "volume")))));
				line.setWeight(Double.parseDouble((parser.getAttributeValue(null, "weight"))));
				
				lines.add(line);
				parser.nextText().trim();
	        } 
	    }
	    
	    transportOrder.setLines(lines);
	    
	}
	
	
}
