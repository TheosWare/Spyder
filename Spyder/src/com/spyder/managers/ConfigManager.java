package com.spyder.managers;

import java.util.ArrayList;
import java.util.List;

import com.spyder.bases.Config;


public class ConfigManager {
		
	private Config mysqlConfig;
	public Config getMysql() { return this.mysqlConfig; }
	
	public ConfigManager()
	{
		this.mysqlConfig = new Config("Mysql") {

			@Override
			public void defaultValues() {
				this.setDefault("Username", "root");
				this.setDefault("Password", "");
				this.setDefault("Host", "localhost");
				this.setDefault("Port", "3306");
				this.setDefault("Database", "Spyder");
			}
			
		};
		
		if(!mysqlConfig.exists())
		{
			mysqlConfig.create();
		}
		
	}

}
