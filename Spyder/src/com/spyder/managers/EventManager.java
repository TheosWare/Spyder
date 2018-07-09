package com.spyder.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
			t.addAddress(e.getAddress().getHostAddress());
		}
		
	}
	
}
