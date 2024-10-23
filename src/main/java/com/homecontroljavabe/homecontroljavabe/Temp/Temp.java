package com.homecontroljavabe.homecontroljavabe.Temp;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class Temp {

	@Id
	private String id;
	private double celsius;
	private double humidity;
	private LocalDateTime timeStamp;

	public Temp(String id, double celsius, double humidity, LocalDateTime timeStamp) {
		this.id = id;
		this.celsius = celsius;
		this.humidity = humidity;
		this.timeStamp = timeStamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getCelsius() {
		return celsius;
	}

	public void setCelsius(double celsius) {
		this.celsius = celsius;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}	
}
