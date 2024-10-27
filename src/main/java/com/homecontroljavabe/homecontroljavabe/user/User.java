package com.homecontroljavabe.homecontroljavabe.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.homecontroljavabe.homecontroljavabe.device.Device;

import java.util.List;
import java.util.ArrayList;

@Document(collection="users")
public class User {
	@Id
	private String id;
	private String username;
	private String password;
	private String bridgeIp;

	private List <Device> deviceList = new ArrayList<>();


	public String getUserId() {
		return id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBridgeIp() {
		return bridgeIp;
	}
	public void setBridgeIp(String bridgeIp) {
		this.bridgeIp = bridgeIp;
	}
	public void setUserId(String userId) {
		this.id = userId;
	}
	public String getUsername() {
		return username;
	}
	public List<Device> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
}
