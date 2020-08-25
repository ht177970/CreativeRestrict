package creative.dru.org;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import creative.dru.org.v.*;

public class Main extends JavaPlugin {
	public static Plugin plugin;
	
	public static WORK w = null;
	
	public void onEnable() {
		plugin = this;
		new DataManager();
		String a = getServer().getClass().getPackage().getName();
		String version = a.substring(a.lastIndexOf('.') + 1);

		if(version.contains("v1_11"))
			w = new WORK_v11();
		else if(version.contains("v1_12"))
			w = new WORK_v11();
		else if(version.contains("v1_13"))
			w = new WORK_v13();
		else if(version.contains("v1_14"))
			w = new WORK_v13();
		else if(version.contains("v1_15"))
			w = new WORK_v13();
		else if(version.contains("v1_16"))
			w = new WORK_v13();
		else
			getLogger().severe("unsupport version.");
		getCommand("¤p­D").setExecutor(new Commands());
		Bukkit.getPluginManager().registerEvents(new EventManager(), this);
		
	}
	
	public void onDisable() {
		Bukkit.getWorlds().forEach(e->{
			if(FileManager.locs.containsKey(e.getName()))
			FileManager.write(e);
			});
	}
}