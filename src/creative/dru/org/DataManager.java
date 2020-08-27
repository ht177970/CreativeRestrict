package creative.dru.org;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;



public class DataManager {
	
//	private static HashMap<String, FileConfiguration> Worlds = new HashMap<String, FileConfiguration>();
	private static File config = new File("plugins/CreativeRestrict/Config.yml");
	public static FileConfiguration Config;
	public static FileConfiguration lang;
	
	
	public DataManager() {
		if(!config.exists()) {
			try {
				File dir = new File("plugins/CreativeRestrict");
				if(!dir.exists()) dir.mkdir();
				config.createNewFile();
				
				
//				Config.set("can_not_drop_item", "§c你沒有足夠權限在創造模式下丟東西!");
//				Config.set("place_liquid", "§c你沒有足夠權限在創造模式下放置液體!");
//				Config.set("place_fire", "§c你沒有足夠權限在創造模式下放火!");
//				Config.set("place_spawn_egg", "§c你沒有足夠權限在創造模式下使用生怪蛋!");
//				Config.set("place_block", "§c你沒有足夠權限在創造模式下'放置此方塊!");
//				Config.set("place_item", "§c你沒有足夠權限在創造模式下'放置此物品!");
//				Config.set("open_container", "§f你沒有足夠權限在創造模式下'開啟容器!");
//				Config.set("chanage_gamemode_broadcast", "§f{player} 已將遊戲模式切換為 {gamemode}!");
				
//				Config.set("can_not_drop_item", "§cYou don't have enough permission to drop item(s) on creative mode!");
//				Config.set("place_liquid", "§cYou don't have enoguh permission to place liquid on creative mode!");
//				Config.set("place_fire", "§cYou don't have enough permission to use fire on creative mode!");
//				Config.set("place_spawn_egg", "§cYou don't have enough permission to use eggs on creative mode!");
//				Config.set("place_block", "§cYou don't have enough permission to place this block on creative mode!");
//				Config.set("place_item", "§cYou don't habe enough permission to use this item!");
//				Config.set("open_container", "§fYou don't have enough permission to open container on creative mode!");
//				Config.set("chanage_gamemode_broadcast", "§f{player} has chanage to {gamemode} mode!");
				try(OutputStreamWriter writer = new OutputStreamWriter((OutputStream)new FileOutputStream(config), Charsets.UTF_8)){
					writer.write("#Support Langs: English, Chinese");
					writer.flush();
				}
				Config = YamlConfiguration.loadConfiguration(config);
				setup();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Config = YamlConfiguration.loadConfiguration(config);
			File l = new File("plugins/CreativeRestrict/Lang/" + Config.getString("Lang") + ".yml");
			if(!l.exists()) {
				setup();
				return;
			}
			lang = YamlConfiguration.loadConfiguration(l);
			String[] key = {"can_not_drop_item","place_liquid","place_fire","place_spawn_egg","place_block","place_item","open_container"};
			String[] value = {"drop item(s)","place liquid","use fire","use eggs","place this block","place this item","open container"};
			for(int i = 0;i < 7;i++) {
				String s = lang.getString(key[i]);
				if(s == null || s.isEmpty())
					lang.set(key[i], "§cYou don't have enough permission to " + value[i] + " on creative mode!");
			}
			String ss = lang.getString("chanage_gamemode_broadcast");
			if(ss == null || ss.isEmpty())
				lang.set("chanage_gamemode_broadcast", "§f{player} has change to {gamemode} mode!");
			try {
				lang.save(l);
			} catch (IOException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace();
			}
		}
	}
	
	private static void setup() {
		try {
			Config.set("Lang", "English");
			new File("plugins/CreativeRestrict/Lang").mkdir();
			String[] names = {"English", "Chinese"};
			for(String name : names) {
				File la = new File("plugins/CreativeRestrict/Lang/" + name + ".yml");
				Files.copy(DataManager.class.getResourceAsStream("/" + name + ".yml"), Paths.get(la.getPath()), StandardCopyOption.REPLACE_EXISTING);
			}
			lang = YamlConfiguration.loadConfiguration(new File("plugins/CreativeRestrict/Lang/" + Config.getString("Lang") + ".yml"));
			Config.save(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			Bukkit.getLogger().severe("can not save player data for "+p.getName()+"!");
			e.printStackTrace();
 		}
	}
	
//	public static void saveConfig() {
//		
//		try {
//			Config.save(config);
//		}catch(Exception e){
//			Bukkit.getLogger().severe(e.getMessage());
//			e.printStackTrace();
//		}
//	}
	
	public static void reloadAll() {
		Config = YamlConfiguration.loadConfiguration(new File("plugins/CreativeRestrict/Config.yml"));
		File l = new File("plugins/CreativeRestrict/Lang/" + Config.getString("Lang") + ".yml");
		if(!l.exists()) {
			setup();
			return;
		}
		lang = YamlConfiguration.loadConfiguration(l);
	}
 	
//	public static void createfile(String fileLocation) {
//		File F = new File(fileLocation);
//		FileConfiguration C = YamlConfiguration.loadConfiguration(F);
//		try {
//			C.save(F);
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//	}
	
//	public static Set<String> getChildren(Configuration C, String o) {
//			return C.getConfigurationSection(o).getKeys(false);
//			
//	}

	
	
}
