package creative.dru.org.v;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public interface WORK {

	public boolean onInteract(Material type, Player p);
	public boolean onInteract(BlockState bs);
	
}
