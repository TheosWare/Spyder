package com.spyder.bases;

public class Address {
	
	private long time;
	public long getTime() { return this.time; }
	
	private String address;
	public String getAddress()
	{
		return this.address;
	}
	
	public Address(String address, long time)
	{
		this.address = address;
		this.time = time;
	}

}
