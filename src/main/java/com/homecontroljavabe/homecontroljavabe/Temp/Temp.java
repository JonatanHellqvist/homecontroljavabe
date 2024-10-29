package com.homecontroljavabe.homecontroljavabe.Temp;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class Temp {

	@Id
	private String id;
	private double celsius;
	private double humidity;
	private double photoTransistorValue;
	private LocalDateTime timeStamp;


	public Temp(String id, double celsius, double humidity, double photoTransistorValue, LocalDateTime timeStamp) {
		this.id = id;
		this.celsius = celsius;
		this.humidity = humidity;
		this.photoTransistorValue = photoTransistorValue;
		this.timeStamp = timeStamp;
	}

	public String getId() {
		return id;
	}

	public double getPhotoTransistorValue() {
		return photoTransistorValue;
	}

	public void setPhotoTransistorValue(double photoTransistorValue) {
		this.photoTransistorValue = photoTransistorValue;
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
