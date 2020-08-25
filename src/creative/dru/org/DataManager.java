package creative.dru.org;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;



public class DataManager {
	
//	private static HashMap<String, FileConfiguration> Worlds = new HashMap<String, FileConfiguration>();
	private static File config = new File("plugins/CreativeRestrict/Config.yml");
	public static FileConfiguration Config;
	
	
	public DataManager() {
		if(!config.exists()) {
			try {
				File dir = new File("plugins/CreativeRestrict");
				if(!dir.exists()) dir.mkdir();
				config.createNewFile();
				
				Config = YamlConfiguration.loadConfiguration(config);
				
//				Config.set("can_not_drop_item", "§c你沒有足夠權限在創造模式下丟東西!");
//				Config.set("place_liquid", "§c你沒有足夠權限在創造模式下放置液體!");
//				Config.set("place_fire", "§c你沒有足夠權限在創造模式下放火!");
//				Config.set("place_spawn_egg", "§c你沒有足夠權限在創造模式下使用生怪蛋!");
//				Config.set("place_block", "§c你沒有足夠權限在創造模式下'放置此方塊!");
//				Config.set("place_item", "§c你沒有足夠權限在創造模式下'放置此物品!");
//				Config.set("chanage_gamemode_broadcast", "§f{player} 已將遊戲模式切換為 {gamemode}!");
				
				Config.set("can_not_drop_item", "§cYou don't have enough permission to drop item(s) on creative mode!");
				Config.set("place_liquid", "§cYou don't have enoguh permission to place liquid on creative mode!");
				Config.set("place_fire", "§cYou don't have enough permission to use fire on creative mode!");
				Config.set("place_spawn_egg", "§cYou don't have enough permission to use eggs on creative mode!");
				Config.set("place_block", "§cYou don't have enough permission to place this block on creative mode!");
				Config.set("place_item", "§cYou don't habe enough permission to use this item!");
				Config.set("open_container", "§fYou don't have enough permission to open container on creative mode!");
				Config.set("chanage_gamemode_broadcast", "§f{player} has chanage to {gamemode} mode!");
				
				Config.save(config);
			} catch (IOException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
			}
		}

//		} else {
			Config = YamlConfiguration.loadConfiguration(config);
			String[] key = {"can_not_drop_item","place_liquid","place_fire","place_spawn_egg","place_block","place_item","open_container"};
			String[] value = {"drop item(s)","place liquid","use fire","use eggs","place this block","place this item","open container"};
			for(int i = 0;i < 7;i++) {
				String s = Config.getString(key[i]);
				if(s == null || s.isEmpty())
					Config.set(key[i], "§cYou don't have enough permission to " + value[i] + " on creative mode!");
			}
			String ss = Config.getString("chanage_gamemode_broadcast");
			if(ss == null || ss.isEmpty())
				Config.set("chanage_gamemode_broadcast", "§f{player} has change to {gamemode} mode!");
			try {
				Config.save(config);
			} catch (IOException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
			}
//		}
//		Config.options().copyDefaults(true );
//		Config.set("Machine.Nothing", );
	}
	
	public static FileConfiguration getPlayerData(Player p) {
		File pd =new File("plugins/CreativeRestrict/playerData/"+p.getUniqueId()+".yml");
		return YamlConfiguration.loadConfiguration(pd);
	}
	
	public static void savePlayerData(Player p, FileConfiguration c) {
		File pd =new File("plugins/CreativeRestrict/playerData/"+p.getUniqueId()+".yml");
		try {
			c.save(pd);
		} catch (IOException e) {
			// TODO 自動產生的 catch 區塊
			Bukkit.getLogger().severe("can not save player data for"+p.getName()+"!");
			e.printStackTrace();
 		}
	}
	
	public static void saveConfig() {
		
		try {
			Config.save(config);
		}catch(Exception e){
			Bukkit.getLogger().severe(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void reloadAll() {
		Config = YamlConfiguration.loadConfiguration(new File("plugins/CreativeRestrict/Config.yml"));
	}
 	
	public static void createfile(String fileLocation) {
		File F = new File(fileLocation);
		FileConfiguration C = YamlConfiguration.loadConfiguration(F);
		try {
			C.save(F);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static Set<String> getChildren(Configuration C, String o) {
			return C.getConfigurationSection(o).getKeys(false);
			
	}

	
	
}
