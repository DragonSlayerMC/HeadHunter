package me.src.HeadHunter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Random;

public class KillEvent implements Listener {
	
	Main plugin;
	
	public KillEvent(Main pl) {
		this.plugin = pl;
	}
	
	
	@EventHandler
	public void onKillEvent(EntityDeathEvent e) {
		
		if(e.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
			
		if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
			Player p =  (Player) e.getEntity();
			Random rand = new Random();
			int n = rand.nextInt(100) + 1;
			
			if(n <= plugin.getConfig().getInt("player-head-drop-chance") || n >= 100) {
				if(plugin.getConfig().getBoolean("put-head-in-inventory-on-death") == true) {
					
					
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta skullm = (SkullMeta) skull.getItemMeta();
					skullm.setOwner(p.getName());
					skullm.setDisplayName(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("head-drop-display-name").replaceAll("%PLAYER%", p.getName()))));
					String owner = skullm.getOwner();
					double balance = Main.getEconomy().getBalance(owner);
					float price = (float) ((plugin.getConfig().getDouble("price-percent") * 0.01) * balance);
					ArrayList<String> list = new ArrayList<>();
					String newstring = plugin.addPrefix(plugin.getConfig().getString("headlore").replaceAll("%PRICE%", String.valueOf(price)));
					list.add(ChatColor.translateAlternateColorCodes('&', newstring));
					skullm.setLore(list);
					skull.setItemMeta(skullm);
					//p.getKiller().getInventory().addItem(skull);
					plugin.addSkull1(skull, p.getKiller().getPlayer().getInventory());
					
					p.getKiller().sendMessage(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("head-add-to-inventory").replaceAll("%PLAYER%", p.getName()))));
				
				
				} else {
					
					
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta skullm = (SkullMeta) skull.getItemMeta();
					skullm.setDisplayName(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("head-drop-display-name").replaceAll("%PLAYER%", p.getName()))));
					skullm.setOwner(p.getName().toString());
					float price = plugin.getHeadPrice(skullm);
					ArrayList<String> list = new ArrayList<String>();
					
					String newstring = plugin.addPrefix(plugin.getConfig().getString("headlore").replaceAll("%PRICE%", String.valueOf(price)));
					
					
					list.add(ChatColor.translateAlternateColorCodes('&', newstring));
					skullm.setLore(list);
					skull.setItemMeta(skullm);
					p.getWorld().dropItem(p.getLocation(), skull);
					
					
				}
				
				
				
			}
				
			
		} else  { 
			
			
			Random rand = new Random();
			int n = rand.nextInt(100) + 1;
			if(n <= plugin.getConfig().getInt("mob-head-drop-chance") || n >= 100) {
				if(plugin.getConfig().getBoolean("put-mob-head-in-inventory-on-death") == true) {
					if(e.getEntity().getKiller() instanceof Player) {
					Player p =  (Player) e.getEntity().getKiller();
					EntityType mob = e.getEntityType();
					String mobs;
					if(mob.getName() == "Skeleton") {
						Skeleton Skeleton = (Skeleton) e.getEntity();
						String skeletype = Skeleton.getSkeletonType().toString();
						mobs = skeletype;
						if(mobs == "NORMAL") {
							mobs = "Skeleton";
						} else {
							mobs = "WSkeleton";
						}
					} else if(mob.getName() == "VillagerGolem") {
						mobs = "Golem";
					} else if(mob.getName() == "ozelot") {
						
						mobs = "Ocelot";
					}else {
					
					mobs = mob.getName();
					}
					
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta skullm = (SkullMeta) skull.getItemMeta();
					skullm.setOwner("MHF_" + mobs);
					skull.setItemMeta(skullm);
					skullm.setDisplayName(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mob-head-drop-display-name").replaceAll("%MOB%", mobs))));
					float price = (float) ((plugin.getConfig().getDouble(mobs + "-price")));
					ArrayList<String> list = new ArrayList<>();
					String newstring = plugin.addPrefix(plugin.getConfig().getString("mob-headlore").replace("%PRICE%", String.valueOf(price)));
					list.add(ChatColor.translateAlternateColorCodes('&', newstring));
					skullm.setLore(list);
					skull.setItemMeta(skullm);
					
					
					
					plugin.addSkull1(skull, p.getInventory());
					
					
					}
				} else {
					
					EntityType mob = e.getEntityType();
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta skullm = (SkullMeta) skull.getItemMeta();
					
					String mobs;
					if(mob.getName() == "Skeleton") {
						Skeleton Skeleton = (Skeleton) e.getEntity();
						String skeletype = Skeleton.getSkeletonType().toString();
						mobs = skeletype;
						if(mobs == "NORMAL") {
							mobs = "Skeleton";
						} else {
							mobs = "WSkeleton";
						}
					} else if(mob.getName() == "VillagerGolem") {
						mobs = "Golem";
					} else if(mob.getName() == "ozelot") {
						
						mobs = "Ocelot";
					}else {
					
					mobs = mob.getName();
					}
					skullm.setDisplayName(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mob-head-drop-display-name").replaceAll("%MOB%", mobs))));
					skullm.setOwner("MHF_" + mobs);
					float price = (float) ((plugin.getConfig().getDouble(mobs + "-price")));
					ArrayList<String> list = new ArrayList<>();
					String newstring = plugin.addPrefix(plugin.getConfig().getString("mob-headlore").replaceAll("%PRICE%", String.valueOf(price)));
					list.add(ChatColor.translateAlternateColorCodes('&', newstring));
					skullm.setLore(list);
					skull.setItemMeta(skullm);
					e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), skull);
					
					
				}
			}
		
		
		}
	
	
	} else {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta skullm = (SkullMeta) skull.getItemMeta();
			skullm.setDisplayName(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("head-drop-display-name").replaceAll("%PLAYER%", p.getName()))));
			skullm.setOwner(p.getName().toString());
			float price = plugin.getHeadPrice(skullm);
			ArrayList<String> list = new ArrayList<String>();
			
			String newstring = plugin.addPrefix(plugin.getConfig().getString("headlore").replaceAll("%PRICE%", String.valueOf(price)));
			
			
			list.add(ChatColor.translateAlternateColorCodes('&', newstring));
			skullm.setLore(list);
			skull.setItemMeta(skullm);
			p.getWorld().dropItem(p.getLocation(), skull);
			
			
			
			
		} else {
			
			EntityType mob = e.getEntityType();
			ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta skullm = (SkullMeta) skull.getItemMeta();
			
			String mobs;
			if(mob.getName() == "Skeleton") {
				Skeleton Skeleton = (Skeleton) e.getEntity();
				String skeletype = Skeleton.getSkeletonType().toString();
				mobs = skeletype;
				if(mobs == "NORMAL") {
					mobs = "Skeleton";
				} else {
					mobs = "WSkeleton";
				}
			} else if(mob.getName() == "VillagerGolem") {
				mobs = "Golem";
			} else if(mob.getName() == "ozelot") {
				
				mobs = "Ocelot";
			}else {
			
			mobs = mob.getName();
			}
			skullm.setDisplayName(plugin.addPrefix(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("mob-head-drop-display-name").replaceAll("%MOB%", mobs))));
			skullm.setOwner("MHF_" + mobs);
			float price = (float) ((plugin.getConfig().getDouble(mobs + "-price")));
			ArrayList<String> list = new ArrayList<>();
			String newstring = plugin.addPrefix(plugin.getConfig().getString("mob-headlore").replaceAll("%PRICE%", String.valueOf(price)));
			list.add(ChatColor.translateAlternateColorCodes('&', newstring));
			skullm.setLore(list);
			skull.setItemMeta(skullm);
			e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), skull);
			
			
			
			
			
			
		}
		
		
		
		
	}

}
}
