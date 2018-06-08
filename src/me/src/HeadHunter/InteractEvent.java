package me.src.HeadHunter;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;



public class InteractEvent implements Listener {
	
	public static ArrayList<String> headlist() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("MHF_Blaze");
		list.add("MHF_CaveSpider");
		list.add("MHF_Zombie");
		list.add("MHF_Chicken");
		list.add("MHF_Cow");
		list.add("MHF_Golem");
		list.add("MHF_Creeper");
		list.add("MHF_Enderman");
		list.add("MHF_Ghast");
		list.add("MHF_LavaSlime");
		list.add("MHF_MushroomCow");
		list.add("MHF_Ocelot");
		list.add("MHF_Pig");
		list.add("MHF_PigZombie");
		list.add("MHF_Sheep");
		list.add("MHF_Skeleton");
		list.add("MHF_Slime");
		list.add("MHF_Spider");
		list.add("MHF_Squid");
		list.add("MHF_Villager");
		list.add("MHF_WSkeleton");
		return list;
	}
	
	
	
	Main plugin;
	
	
	public InteractEvent(Main pl) {
		this.plugin = pl;
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if(e.getPlayer().getItemInHand().getType() == Material.SKULL_ITEM) {
			
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ItemStack head = e.getPlayer().getItemInHand();
				SkullMeta headm = (SkullMeta) head.getItemMeta();
				e.setCancelled(true);
				if(plugin.getConfig().getBoolean("sell-heads-on-right-click") == true) {
					if(!(headlist().contains(headm.getOwner()))) {
					float sellamount = 0;
					int amount = 0;
					Inventory inv = e.getPlayer().getInventory();
					
					for(int i=0; inv.getSize() >= i; i++) {
						ItemStack item = inv.getItem(i);
						if(item != null) {
						if(item.getType() == Material.SKULL_ITEM) {
						SkullMeta itemm = (SkullMeta) item.getItemMeta();
						if(itemm.getOwner().toString().equals(headm.getOwner().toString())) {
							sellamount += item.getAmount() * plugin.getHeadPriceI(item.getItemMeta());
							amount += item.getAmount();
							inv.setItem(i, new ItemStack(Material.AIR));
							
						}
						}
						}
					}
					
					Main.getEconomy().depositPlayer(e.getPlayer().getName(), sellamount);
					Main.getEconomy().withdrawPlayer(headm.getOwner().toString(), sellamount);
					String mystring =  plugin.getConfig().getString("sell-message");
					mystring = mystring.replace("%SELLAMOUNT%", String.valueOf(sellamount));
					mystring = mystring.replace("%AMOUNT%", String.valueOf(amount));
					mystring = mystring.replace("%PLAYERHEAD%", headm.getOwner());
					e.getPlayer().sendMessage(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', mystring)));
					
				
			} else {
				
				float sellamount = 0;
				int amount = 0;
				Inventory inv = e.getPlayer().getInventory();
				
				for(int i=0; inv.getSize() >= i; i++) {
					ItemStack item = inv.getItem(i);
					if(item != null) {
					if(item.getType() == Material.SKULL_ITEM) {
					SkullMeta itemm = (SkullMeta) item.getItemMeta();
					if(itemm.getOwner().toString().equals(headm.getOwner().toString())) {
						sellamount += item.getAmount() * plugin.getMobHeadPrice(itemm);
						amount += item.getAmount();
						inv.setItem(i, new ItemStack(Material.AIR));
						
					}
					}
					}
				}
				
				Main.getEconomy().depositPlayer(e.getPlayer().getName(), sellamount);
				Main.getEconomy().withdrawPlayer(headm.getOwner().toString(), sellamount);
				String mystring =  plugin.getConfig().getString("mob-sell-message");
				mystring = mystring.replace("%SELLAMOUNT%", String.valueOf(sellamount));
				mystring = mystring.replace("%AMOUNT%", String.valueOf(amount));
				mystring = mystring.replace("%MOBHEAD%", headm.getOwner().replaceAll("MHF_", ""));
				e.getPlayer().sendMessage(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', mystring)));
				}
				
				
			
			}
				
			}
		
		
	}
	}
	

}
