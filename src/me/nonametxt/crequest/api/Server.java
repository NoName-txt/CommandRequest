package me.nonametxt.crequest.api;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.sun.net.httpserver.HttpServer;

import me.nonametxt.crequest.CommandRequest;
import me.nonametxt.crequest.utf.UTF8Config;

public class Server {
	static HttpServer server;
	private static FileConfiguration myConfig;
	
    public static void Start(CommandRequest crequest) throws IOException {
		myConfig = (FileConfiguration) UTF8Config.loadConfiguration((Plugin) crequest, "message.yml", new File(crequest.getDataFolder(), "config.yml"));

    	Integer port = myConfig.getInt("port");
    	String url = myConfig.getString("url");

        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(url, (exchange -> {
        	String cUsername = myConfig.getString("username");
        	String cPassword = myConfig.getString("password");
            if ("POST".equals(exchange.getRequestMethod())) {
                String responseText = "{\"success\":\"true\"}";
                Map<String, String> params = getParamMap(exchange.getRequestURI().getQuery()); 
                
                String username = params.get("username");
                String password = params.get("password");
                if(!username.equals(cUsername) || !password.equals(cPassword)) {
                	responseText = "{\"success\":\"false\"}";
                }else {
	                String command = params.get("command");
	                if(command != null) {
	                	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	                	Bukkit.dispatchCommand(console, command);
	                }
	        		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e| &b[CommandRequest] &a" + command +" &e|"));
                }

                
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        }));


        server.setExecutor(null); 
        server.start();

    }
    
    public static void Stop(CommandRequest crequest) throws IOException {
    	server.stop(1);
    }
    
    public static Map<String, String> getParamMap(String query) {
        if (query == null || query.isEmpty()) return Collections.emptyMap();
        return Stream.of(query.split("&"))
        		.filter(s -> !s.isEmpty())
        		.map(kv -> kv.split("=", 2))
        		.collect(Collectors.toMap(x -> x[0], x-> x[1]));
    }
}
