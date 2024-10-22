package com.homecontroljavabe.homecontroljavabe;

import org.springframework.data.annotation.Id;

public class HomeDevice {
	
	@Id
	private String deviceId;
	private String deviceName;
	private String deviceType;
	private String deviceStatus;

	public HomeDevice(String deviceId, String deviceName, String deviceType, String deviceStatus) {
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	

	
}
