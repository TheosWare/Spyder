package com.spyder.bases;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.spyder.main.Spyder;


public abstract class Config {
	
	private String name;
	
	private File confFile;
	private FileConfiguration confObj;
	
	public Config(String name)
	{
		this.name = name;
		
		this.confFile = new File(Spyder.getInstance().getDataFolder(), this.name + ".yml");
		if(this.confFile.exists())
		{
			confObj = YamlConfiguration.loadConfiguration(this.confFile);
			this.defaultValues();
		}
		
		
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void create()
	{
		if(!this.confFile.exists())
		{
			try {
				this.confFile.createNewFile();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		this.confObj = YamlConfiguration.loadConfiguration(this.confFile);
		this.defaultValues();
		this.save(true);
	}
	
	public void setDefault(String path, Object value)
	{
		confObj.addDefault(path, value);
	}
	
	public void save(boolean defaults)
	{
		try {
			
			confObj.options().copyDefaults(defaults);
			confObj.save(this.confFile);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Object getValue(String path)
	{
		return confObj.get(path);
	}
	
	public void setValue(String path, Object value)
	{
		confObj.set(path, value);
	}
	
	public boolean exists()
	{
		return confObj != null;
	}
	
	public abstract void defaultValues();

}
