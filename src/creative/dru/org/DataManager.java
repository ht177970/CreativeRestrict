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
				
				Config.set("can_not_drop_item", "��c�A�S�������v���b�гy�Ҧ��U��F��!");
				Config.set("place_liquid", "��c�A�S�������v���b�гy�Ҧ��U��m�G��!");
				Config.set("place_fire", "��c�A�S�������v���b�гy�Ҧ��U���!");
				Config.set("place_spawn_egg", "��c�A�S�������v���b�гy�Ҧ��U�ϥΥͩǳJ!");
				Config.set("place_block", "��c�A�S�������v���b�гy�Ҧ��U'��m�����!");
				Config.set("place_item", "��c�A�S�������v���b�гy�Ҧ��U'��m�����~!");
				Config.set("chanage_gamemode_broadcast", "��f{player} �w�N�C���Ҧ������� {gamemode}!");
				Config.save(config);
			} catch (IOException e) {
				// TODO �۰ʲ��ͪ� catch �϶�
				e.printStackTrace();
			}


		} else {
			Config = YamlConfiguration.loadConfiguration(config);
			String[] key = {"can_not_drop_item","place_liquid","place_fire","place_spawn_egg","place_block","place_item"};
			String[] value = {"��F��","��m�G��","���","�ϥΥͩǳJ","��m�����","��m�����~"};
			for(int i = 0;i < 6;i++) {
				String s = Config.getString(key[i]);
				if(s == null || s.isEmpty())
					Config.set(key[i], "��c�A�S�������v���b�гy�Ҧ��U" + value[i] + "!");
			}
			String ss = Config.getString("chanage_gamemode_broadcast");
			if(ss == null || ss.isEmpty())
				Config.set("chanage_gamemode_broadcast", "��f{player} �w�N�C���Ҧ������� {gamemode}!");
		}
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
			// TODO �۰ʲ��ͪ� catch �϶�
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
