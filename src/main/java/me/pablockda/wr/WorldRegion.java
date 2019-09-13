package me.pablockda.wr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.pablockda.wr.utils.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.pablockda.wr.managers.RegionManager;
import me.pablockda.wr.utils.Config;
import me.pablockda.wr.utils.Mensajes;



public class WorldRegion extends JavaPlugin{

	private String latestversion;
	private PluginDescriptionFile pdfFile = getDescription();
	private String version = this.pdfFile.getVersion();
	
	public static WorldRegion instance;
	
	private Config config = new Config(this, "config");
	
	// Carga del Plugin
	public void onEnable() {
		
		instance = this;
		
		new RegionManager();

		MetricsLite metrics = new MetricsLite(this);
		
		Mensajes.cargaPlugin(this.getDescription().getName(), this.getDescription().getVersion());
		
		this.getCommand("worldregion").setExecutor( new Comands() );
		
		updateChecker();		
		
	}
	
	
	// Descarga del Plugin
	public void onDisable() {
		Mensajes.descargaPlugin(this.getDescription().getName(), this.getDescription().getVersion());
	}
	
	
	private void updateChecker() {
	    try {
	      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(
	        "https://api.spigotmc.org/legacy/update.php?resource=69426").openConnection();
	      int i = 1250;
	      localHttpURLConnection.setConnectTimeout(i);
	      localHttpURLConnection.setReadTimeout(i);
	      this.latestversion = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream())).readLine();
	      if ((this.latestversion.length() <= 7) && 
	        (!this.version.equals(this.latestversion)))
	      {
	        Bukkit.getConsoleSender().sendMessage(Mensajes.ChatOnColor("&4[&6WorldRegion&4] &8>> &cVersion &e(" + this.latestversion + "&e) &cis available."));
	        Bukkit.getConsoleSender().sendMessage(Mensajes.ChatOnColor("&4[&6WorldRegion&4] &8>> &cYou can download it at: &ehttps://www.spigotmc.org/resources/69426/"));
	      }
	    }
	    catch (Exception localException) {
	      Bukkit.getConsoleSender().sendMessage(Mensajes.ChatOnColor("&4[&6WorldRegion&4] &8>> &cError while checking update."));
	    }
	}



	public Config getConfig() {
		return config;
	}
	
	
	
	
}