package me.nonametxt.crequest.utf;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class UTF8Config extends YamlConfiguration {
	public static UTF8Config loadConfiguration(File file) {
		UTF8Config utf8c = new UTF8Config();
		try {
			utf8c.load(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
		} catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		return utf8c;
	}

	public static UTF8Config loadConfiguration(Plugin plugin, String resource, File file) {
		InputStream defaultsStream = plugin.getResource(resource);

		if (!file.exists()) {
			try {
				Files.createParentDirs(file);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		UTF8Config conf = new UTF8Config();
		conf.loadUtf8(file);

		if (defaultsStream != null) {
			UTF8Config defaults = new UTF8Config();
			try {
				defaults.load(new InputStreamReader(defaultsStream, Charset.forName("UTF-8")));
				conf.setDefaults((Configuration) defaults);
				conf.options().copyDefaults(true);

				conf.save(file);
			} catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
				e.printStackTrace();
				plugin.getLogger().warning("Failed to load defaults for " + file.getAbsolutePath());
			}
		}

		return conf;
	}

	public void loadUtf8(File file) {
		try {
			load(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
		} catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void load(File f) {
		loadUtf8(f);
	}

	public void save(File file) throws IOException {
		Validate.notNull(file, "File cannot be null");
		Files.createParentDirs(file);
		String data = saveToString();
		Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8"));
		try {
			writer.write(data);
		} finally {
			writer.close();
		}
	}
}
