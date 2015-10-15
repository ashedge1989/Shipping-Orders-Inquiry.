package org.odyssey.model;

import java.util.List;

public class Order {

	private int orderId;
	private List<Line> lines;
	private Location from;
	private Location to;
	private String instructions;
	
	
	
	
	
	public List<Line> getLines() {
		return lines;
	}
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	public Location getFrom() {
		return from;
	}
	public void setFrom(Location from) {
		this.from = from;
	}
	public Location getTo() {
		return to;
	}
	public void setTo(Location to) {
		this.to = to;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	
	
	
	
}
