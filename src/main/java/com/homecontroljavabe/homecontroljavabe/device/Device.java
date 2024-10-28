package com.homecontroljavabe.homecontroljavabe.device;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
	@Id
	private String id;
	private String deviceData;
	private int hueIndex;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceData() {
		return deviceData;
	}
	public void setDeviceData(String deviceData) {
		this.deviceData = deviceData;
	}
	public int getHueIndex() {
		return hueIndex;
	}
	public void setHueIndex(int hueIndex) {
		this.hueIndex = hueIndex;
	}

	
}
