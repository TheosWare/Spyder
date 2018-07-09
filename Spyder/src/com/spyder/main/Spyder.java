package com.spyder.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.profiler.main.Profiler;
import com.spyder.bases.Address;
import com.spyder.bases.Target;
import com.spyder.managers.ConfigManager;
import com.spyder.managers.EventManager;
import com.spyder.managers.TargetManager;

public class Spyder extends JavaPlugin{
	
	private static Spyder instance;
	public static Spyder getInstance() { return instance; }
	
	private Profiler profiler;
	public Profiler getProfiler() { return this.profiler; }
	
	private ConfigManager configManager;
	public ConfigManager getConfigManager() { return this.configManager; }
	
	
	private TargetManager targetManager;
	public TargetManager getTargetManager() { return this.targetManager; }
	
	private final static String PREFIX = ChatColor.translateAlternateColorCodes('&', "&7[&8Spyder&7]&r ");
	
	@Override
	public void onEnable()
	{
		Spyder.instance = this;
		
		
		this.profiler = (Profiler)this.getServer().getPluginManager().getPlugin("Profiler");
		
		if(this.profiler == null)
		{
			this.getLogger().info("Profiler not loaded, please try again.");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		
		if(!this.getDataFolder().exists()) { this.getDataFolder().mkdirs(); }
		
		this.configManager = new ConfigManager();
		
		
		
		this.getServer().getPluginManager().registerEvents(new EventManager(), this);
		
		this.targetManager = new TargetManager();
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if(cmd.getName().equalsIgnoreCase("spy"))
		{
			
				if(args.length == 0)
				{
					if(sender.hasPermission("spyder.help"))
					{
						sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7Syntax: &8/Spy <option> [value]"));
						sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7use /Spy options for more."));

					}
				}else if(args.length >= 1)
				{
					String option = args[0].toLowerCase();
					
					switch(option)
					{
					case "options":
						if(sender.hasPermission("spyder.help"))
						{
							sender.sendMessage("");
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "List of command option's."));
							sender.sendMessage("");
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7Help &8> &7Help page."));
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7Options &8> &7This page."));
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7Reset [username] &8> &7Reset a players log."));
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7Clear &8> &7Clear the player cache."));
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7Lookup [username] &8> &7View a players log."));

						}
						break;
					case "help":
						if(sender.hasPermission("spyder.help"))
						{
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7Syntax: &8/Spy <option> [value]"));
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "&7use /Spy options for more."));

						}
						break;
					case "reset":
						if(sender.hasPermission("spyder.reset"))
						{
							if(args.length <= 1)
							{
								sender.sendMessage(this.PREFIX + "Invalid syntax, use /Spy help for more.");
								return true;
							}
							String name = args[1];
							OfflinePlayer op = this.getServer().getOfflinePlayer(name);
							if(op == null)
							{
								sender.sendMessage(this.PREFIX + "Unable to find player.");
								return true;
							}
							
							Target t = this.getTargetManager().getTarget(op.getUniqueId());
							t.resetAddresses();
							sender.sendMessage(this.PREFIX + "Player log reset.");
						}
						break;
					case "clear":
						if(sender.hasPermission("spyder.clear"))
						{
							this.targetManager.getTargetCache().clear();
							sender.sendMessage(this.PREFIX + "Target cache reset.");

						}
						break;
					case "lookup":
						if(sender.hasPermission("spyder.lookup"))
						{
							if(args.length <= 1)
							{
								sender.sendMessage(this.PREFIX + "Invalid syntax, use /Spy help for more.");
								return true;
							}
							
							String name = args[1];
							OfflinePlayer op = this.getServer().getOfflinePlayer(name);
							if(op == null)
							{
								sender.sendMessage(this.PREFIX + "Unable to find player.");
								return true;
							}
							
							int page = 1;
							if(args.length >= 3)
							{
								String pageRaw = args[2];
								try {
									page = Integer.parseInt(pageRaw);
								}catch(Exception e)
								{
									sender.sendMessage(this.PREFIX + "Invalid page number.");
								}
								
								if(page < 1)
								{
									page = 1;
								}
								
							}

							Target t = this.getTargetManager().getTarget(op.getUniqueId());
							List<Address> logs = t.getIPs();
							int p = (logs.size() + 5 - 1) / 5;
							sender.sendMessage(this.PREFIX + ChatColor.translateAlternateColorCodes('&', "Showing page " + page + " of " + name + "! &8(" + p + " Total)"));

							int beginshow = (5 * (page - 1));
							int toshow = beginshow + 5;
							
							for(int i = beginshow; i < toshow; i++)
							{
								int ni = i;
								if(logs.size() - 1 >= ni)
								{
									Address a = logs.get(ni);
									Date d = new Date(a.getTime());
									SimpleDateFormat sdf = new SimpleDateFormat("dd MMM YYYY HH:mm a");
									
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&8" + sdf.format(d) + "&7] &8> &7" + a.getAddress()));
									
								}
								
							}
														
						}
						break;
					}
					
					
					
				}else {
					
				}
			
			return true;
		}
		
		
		return false;
	}
}
