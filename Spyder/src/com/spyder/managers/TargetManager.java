package com.spyder.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.spyder.bases.Target;

public class TargetManager {
	
	private List<Target> targetCache = new ArrayList<Target>();
	public List<Target> getTargetCache() { return this.targetCache; };
	public Target getTarget(Player p) { return this.getTarget(p.getUniqueId()); }
	
	public Target getTarget(UUID uuid) {
		for(Target t : targetCache){
			
			if(t.getOwner() == uuid){
				
				return t;
			}
		}
		
		Target newt = new Target(uuid);
		
		targetCache.add(newt);
		return newt;
		
	}
	
	
}
