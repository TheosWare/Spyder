package com.spyder.bases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.profiler.bases.Client;
import com.profiler.bases.Profile;
import com.spyder.main.Spyder;

public class Target {
	
	
	
	//Target's UUID
	private UUID owner;
	public UUID getOwner() { return this.owner; }
	
	//Logins
	private List<Log> logs = new ArrayList<Log>();
	
	public List<Log> getLogins()
	{
		List<Log> res = new ArrayList<Log>();
		for(Log l : this.logs)
		{
			if(l.getType() == 0)
			{
				res.add(l);
			}
		}
		Collections.sort(res, new Sorter());
		return res;
	}
	
	public List<Log> getChats()
	{
		List<Log> res = new ArrayList<Log>();
		for(Log l : this.logs)
		{
			if(l.getType() == 1)
			{
				res.add(l);
			}
		}
		Collections.sort(res, new Sorter());
		return res;
	}
	
	public List<Log> getCommands()
	{
		List<Log> res = new ArrayList<Log>();
		for(Log l : this.logs)
		{
			if(l.getType() == 2)
			{
				res.add(l);
			}
		}
		Collections.sort(res, new Sorter());
		return res;
	}
	
	public void addLog(int type, Object key, Object value) {this.logs.add(new Log(type, key, value)); this.save();}
	public void removeLog(Log address) { logs.remove(address); this.save(); }
	
	
	
	private Client pClient;
	
	public void resetLogs()
	{
		this.logs.clear();
		this.save();
	}
	
	public Target(UUID owner)
	{
		this.owner = owner;
		this.pClient = Spyder.getInstance().getProfiler().getClientManager().getClient(owner);
		
		Profile p = this.pClient.getProfile(Spyder.getInstance());
		
		if(p == null)
		{
			p = this.pClient.addProfile(Spyder.getInstance());
			HashMap<Object, Object> aData = new HashMap<Object, Object>();
			p.setValue("Logins", aData);
			p.setValue("Chats", aData);
			p.setValue("Commands", aData);
			pClient.save();
		}
		HashMap<Object, Object> tempData;
		tempData = (HashMap<Object, Object>) p.getValue("Logins");
		tempData.entrySet().forEach(entry -> {
			this.logs.add(new Log(0, entry.getKey(),entry.getValue()));
		});
		
		
		tempData = (HashMap<Object, Object>) p.getValue("Chats");
		tempData.entrySet().forEach(entry -> {
			this.logs.add(new Log(1, entry.getKey(),entry.getValue()));
		});
		
		tempData = (HashMap<Object, Object>) p.getValue("Commands");
		tempData.entrySet().forEach(entry -> {
			this.logs.add(new Log(2, entry.getKey(),entry.getValue()));
		});
	}
	
	public boolean exists()
	{
		return this.pClient != null;
	}
	
	public void save()
	{
		Profile p = this.pClient.getProfile(Spyder.getInstance());
		
		HashMap<Object, Object> logins = new HashMap<Object, Object>();
		HashMap<Object, Object> chats = new HashMap<Object, Object>();
		HashMap<Object, Object> commands = new HashMap<Object, Object>();

		for(Log a : this.logs)
		{
			switch(a.getType())
			{
			case 0:
				logins.put(a.getKey(), a.getValue());
				break;
			case 1:
				chats.put(a.getKey(), a.getValue());
				break;
			case 2:
				commands.put(a.getKey(), a.getValue());
				break;
			}
		}
		p.setValue("Logins", logins);
		p.setValue("Chats", chats);
		p.setValue("Commands", commands);
		this.pClient.save();
	}

}

class Sorter implements Comparator<Log> {

	  public int compare(Log one, Log two) {
	    return (int) (Long.parseLong(one.getValue().toString()) - Long.parseLong(two.getValue().toString()));
	  }

}
