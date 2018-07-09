package com.spyder.bases;

public class Log {
	
	private int type;
	
	private Object key;
	private Object value;
	
	public Log(int type, Object key, Object value)
	{
		this.type = type;
		this.key = key;
		this.value = value;
	}
	
	public int getType()
	{
		return this.type;
	}
	
	public Object getKey()
	{
		return this.key;
	}
	
	public Object getValue()
	{
		return this.value;
	}

}
