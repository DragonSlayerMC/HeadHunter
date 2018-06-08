package me.src.HeadHunter;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Skull;

public class ClickEvent implements Listener {
	
	Main plugin;
	
	public ClickEvent(Main pl) {
		this.plugin = pl;
		
	}
	
	@EventHandler
	public void onClick(org.bukkit.event.inventory.InventoryClickEvent e) {
		if(e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
				Player p = (Player) e.getWhoClicked();
				ItemStack skull = e.getCurrentItem();
				SkullMeta skullm = (SkullMeta) skull.getItemMeta();
				if(!(InteractEvent.headlist().contains(skullm.getOwner()))) {
					float price = plugin.getHeadPrice(skullm);
					ArrayList<String> list = new ArrayList<String>();
					String newstring = plugin.addPrefix(plugin.getConfig().getString("headlore").replaceAll("%PRICE%", String.valueOf(price)));
					list.add(ChatColor.translateAlternateColorCodes('&', newstring));
					skullm.setLore(list);
					skull.setItemMeta(skullm);
				
				
			
				}
			
		}
		
		
		
	}

}
