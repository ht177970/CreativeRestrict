package creative.dru.org.v;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import creative.dru.org.DataManager;

public class WORK_v13 implements WORK{

	@Override
	public boolean onInteract(Material type, Player p) {
		switch(type) {
		case WATER_BUCKET: case LAVA_BUCKET: case PUFFERFISH_BUCKET: case SALMON_BUCKET: 
		case COD_BUCKET: case TROPICAL_FISH_BUCKET:
			p.sendMessage(DataManager.Config.getString("place_liquid"));
			return true;
		case BEDROCK: case OBSIDIAN: case END_PORTAL_FRAME: case BARRIER: case STRUCTURE_BLOCK:
		case STRUCTURE_VOID: case COMMAND_BLOCK:
			p.sendMessage(DataManager.Config.getString("place_block"));
			return true;
		case TNT_MINECART: case COMMAND_BLOCK_MINECART:
			p.sendMessage(DataManager.Config.getString("place_item"));
			return true;
		default:
			break;
		}
		return false;
	}
}
