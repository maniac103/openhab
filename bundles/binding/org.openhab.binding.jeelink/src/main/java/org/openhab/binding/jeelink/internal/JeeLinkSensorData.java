package org.openhab.binding.jeelink.internal;

import java.math.BigDecimal;

public class JeeLinkSensorData {

	private String id;

	private BigDecimal temperature;

	private BigDecimal humidity;

	private boolean lowBattery;

	private boolean newBattery;

	public JeeLinkSensorData(String id, double temperature, int humidity, 
			boolean lowBattery, boolean newBattery) {

		this.id = id;
		this.lowBattery = lowBattery;
		this.newBattery = newBattery;
		this.temperature = new BigDecimal(temperature);
		this.humidity = new BigDecimal(humidity);
	}
	
	public JeeLinkSensorData(String id, BigDecimal temperature, BigDecimal humidity, 
			boolean lowBattery, boolean newBattery) {

		this.id = id;
		this.lowBattery = lowBattery;
		this.newBattery = newBattery;
		this.temperature = temperature;
		this.humidity = humidity;
	}

	public String getId() {
		return id;
	}

	public BigDecimal getTemperature() {
		return temperature;
	}

	public BigDecimal getHumidity() {
		return humidity;
	}

	public boolean isLowBattery() {
		return lowBattery;
	}

	public boolean isNewBattery() {
		return newBattery;
	}


}
