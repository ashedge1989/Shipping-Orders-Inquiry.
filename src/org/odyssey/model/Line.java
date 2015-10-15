package org.odyssey.model;

public class Line {

	private boolean hazard=false;
	private String product;
	private Double volume;
	private Double weight;
	
	
	
	
	public boolean isHazard() {
		return hazard;
	}
	public void setHazard(boolean hazard) {
		this.hazard = hazard;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
}
