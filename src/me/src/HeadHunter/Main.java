package me.src.HeadHunter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;


public class Main extends JavaPlugin {
	
	private static Main plugin;
	public String prefix;
	public String version;
	
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
    
	
	
	PluginManager pm = Bukkit.getPluginManager();
	
	public void onEnable() {
		this.saveDefaultConfig();
		plugin = this;
		prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));
		pm.registerEvents(new ClickEvent(plugin), this);
		pm.registerEvents(new KillEvent(plugin), this);
		pm.registerEvents(new InteractEvent(plugin), this);
		
		
		if (!setupEconomy() ) {
	         log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
	         getServer().getPluginManager().disablePlugin(this);
	         return;
	     }
	     setupPermissions();
	     setupChat();
		
	     
		
		version = this.getServer().getVersion();
		 //this.getConfig().options().copyDefaults(true);
		 //saveConfig();
		
		
		
	}
	
	 public void onDisable() {
		 log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
	  getLogger().info("|-----------------------------------------|");

	  }
	
	
	
	
	
	
	 private boolean setupEconomy() {
	     if (getServer().getPluginManager().getPlugin("Vault") == null) {
	         return false;
	     }
	     RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	     if (rsp == null) {
	         return false;
	     }
	     econ = rsp.getProvider();
	     return econ != null;
	 }
	 
	 private boolean setupChat() {
	     RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
	     chat = rsp.getProvider();
	     return chat != null;
	 }
	 
	 private boolean setupPermissions() {
	     RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
	     perms = rsp.getProvider();
	     return perms != null;
	 }
	 public static Economy getEconomy() {
	     return econ;
	 }
	 
	 public static Permission getPermissions() {
	     return perms;
	 }
	 
	 public static Chat getChat() {
	     return chat;
	 }
	

	 
	 public float getHeadPrice(SkullMeta skullm) {
			String owner = skullm.getOwner();
			double balance = getEconomy().getBalance(owner);
			float price = (float) ((plugin.getConfig().getDouble("price-percent") * 0.01) * balance);
			return price;
		 
	 }
	 
	 public float getHeadPriceI(ItemMeta skull) {
		 	SkullMeta skullm = (SkullMeta) skull;
			String owner = skullm.getOwner().toString();
			double balance = getEconomy().getBalance(owner);
			float price = (float) ((plugin.getConfig().getDouble("price-percent") * 0.01) * balance);
			return price;
		 
	 }
	 
	 
	 public String addPrefix(String s) {
		 s = s.replaceAll("%PREFIX%", prefix);
		 return s;
		 
		 
	 }
	 
	 
	 public float getMobHeadPrice(SkullMeta skullm) {	
			String mobs;
			mobs = skullm.getOwner().replaceAll("MHF_", "");
			float price = (float) ((plugin.getConfig().getDouble(mobs + "-price")));
			
			
			return price;
		 
		 
	 }
	 
	 
	 
	    public void addSkull1(ItemStack skull, PlayerInventory inventory) {

	        String displayName = skull.getItemMeta().getDisplayName();
	        
	        
	        Collection<? extends ItemStack> skulls = inventory.all(Material.SKULL_ITEM).values();

	        ItemStack found = skulls.stream()
	                .filter(ItemStack::hasItemMeta)
	                .filter(it -> it.getAmount() < 64)
	                .filter(it -> it.getItemMeta().getDisplayName().equals(displayName))
	                .findFirst().orElse(null);
	        
	        
	        if (found == null) inventory.addItem(skull);
	        else found.setAmount(found.getAmount() + 1);

	    }
	 
	 
	 
}
