package com.spyder.bases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.profiler.bases.Client;
import com.profiler.bases.Profile;
import com.spyder.main.Spyder;

public class Target {
	
	
	
	//Target's UUID
	private UUID owner;
	public UUID getOwner() { return this.owner; }
	
	//Logged addresses
	private List<Address> addresses = new ArrayList<Address>();
	public List<Address> getIPs() { Collections.sort(this.addresses, new AddressSequenceComparator()); return this.addresses; }
	
	
	public void addAddress(String address) { this.addAddress(address, System.currentTimeMillis());}
	public void addAddress(String address, long time) {this.addAddress(new Address(address, time));}
	public void addAddress(Address address) {this.addresses.add(address); this.save();}
	
	public void removeAddress(Address address) { addresses.remove(address); this.save(); }
	
	private Client pClient;
	
	public void resetAddresses()
	{
		this.addresses.clear();
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
			pClient.save();
		}
		
		HashMap<Object, Object> aData = (HashMap<Object, Object>) p.getValue("Logins");
		aData.entrySet().forEach(entry -> {
			this.addresses.add(new Address(entry.getValue().toString(),Long.parseLong(entry.getKey().toString())));
		});		
	}
	
	public boolean exists()
	{
		return this.pClient != null;
	}
	
	public void save()
	{
		Profile p = this.pClient.getProfile(Spyder.getInstance());
		
		HashMap<Object, Object> aData = new HashMap<Object, Object>();
		for(Address a : this.addresses)
		{
			aData.put(a.getTime(), a.getAddress());
		}
		p.setValue("Logins", aData);
		this.pClient.save();
	}

}

class AddressSequenceComparator implements Comparator<Address> {

	  public int compare(Address one, Address two) {
	    return (int) (one.getTime() - two.getTime());
	  }

}
