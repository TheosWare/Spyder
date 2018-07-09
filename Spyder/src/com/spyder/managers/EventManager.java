package com.spyder.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.spyder.bases.Target;
import com.spyder.main.Spyder;

public class EventManager implements Listener {
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e)
	{
		Target t = Spyder.getInstance().getTargetManager().getTarget(e.getPlayer());
		if(t.exists())
		{
			t.addLog(0, System.currentTimeMillis(), e.getAddress().getHostAddress());
		}
		
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Target t = Spyder.getInstance().getTargetManager().getTarget(e.getPlayer());
		if(t.exists())
		{
			t.addLog(1, System.currentTimeMillis(), e.getMessage());
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e)
	{
		Target t = Spyder.getInstance().getTargetManager().getTarget(e.getPlayer());
		if(t.exists())
		{
			t.addLog(2, System.currentTimeMillis(), e.getMessage());
		}
	}
	
}
