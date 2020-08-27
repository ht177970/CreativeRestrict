package creative.dru.org.v;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lockable;
import org.bukkit.entity.Player;

import creative.dru.org.DataManager;

public class WORK_v11 implements WORK{

	@Override
	public boolean onInteract(Material type, Player p) {
		switch(type) {
			case WATER_BUCKET: case LAVA_BUCKET:
				p.sendMessage(DataManager.Config.getString("place_liquid"));
				return true;
			case FLINT_AND_STEEL:
				p.sendMessage(DataManager.Config.getString("place_fire"));
				return true;
			case BEDROCK: case OBSIDIAN: case BARRIER: case STRUCTURE_BLOCK:
			case STRUCTURE_VOID:
				p.sendMessage(DataManager.Config.getString("place_block"));
				return true;
			default:
				switch(type.name()) {
					case "FIREBALL":
						p.sendMessage(DataManager.Config.getString("place_fire"));
						return true;
					case "ENDER_PORTAL_FRAME": case "COMMAND":
						p.sendMessage(DataManager.Config.getString("place_block"));
						return true;
					case "EXPLOSIVE_MINECART": case "COMMAND_MINECART":
						p.sendMessage(DataManager.Config.getString("place_item"));
						return true;
				}
				break;
		}
		return false;
	}
	
	@Override
	public boolean onInteract(BlockState bs) {
		if(bs instanceof Lockable)
			return true;
		return false;
	}
}
