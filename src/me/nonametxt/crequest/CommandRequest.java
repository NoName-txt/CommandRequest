package me.nonametxt.crequest;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.nonametxt.crequest.api.Server;
import me.nonametxt.crequest.utf.UTF8Config;



public class CommandRequest extends JavaPlugin {
	public FileConfiguration myConfig;
	Listener[] listeners = new Listener[] { };
	
	public void onEnable() {

		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e| &b[CommandRequest] &aOpened &e|"));
		saveDefaultConfig();
		
		this.myConfig = (FileConfiguration) UTF8Config.loadConfiguration((Plugin) this, "config.yml", new File(getDataFolder(), "config.yml"));
		
		try {
			Server.Start(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		registerListeners();
	}
	
	public void onDisable() {
		try {
			Server.Stop(this);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		return true;
	}

	public void reloadConfig() {
		super.reloadConfig();
		this.myConfig = (FileConfiguration) UTF8Config.loadConfiguration((Plugin) this, "config.yml", new File(getDataFolder(), "config.yml"));
	}


	public YamlConfiguration loadConfigUTF8(String resource, File file) {
		YamlConfiguration config = new YamlConfiguration();

		try {
			if (!file.exists()) {
				file.createNewFile();

				InputStream fromJar = getResource(resource);

				if (fromJar != null) {

					YamlConfiguration defaults = new YamlConfiguration();
					defaults.load(new InputStreamReader(fromJar, Charset.forName("UTF-8")));

					config.setDefaults((Configuration) defaults);
					config.options().copyDefaults(true);
					config.save(file);
				}
			}

			config.load(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		return config;
	}

	private void registerListeners() {
		PluginManager plman = Bukkit.getPluginManager();
		Listener[] listeners;
		for (int length = (listeners = this.listeners).length, i = 0; i < length; i++) {
			Listener lt = listeners[i];
			plman.registerEvents(lt, (Plugin) this);
		}
	}


}
