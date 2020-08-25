package creative.dru.org;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.inventory.ItemStack;

public class EventManager implements Listener {
	
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if(!e.getPlayer().hasPermission("creative.res.dropitem")&&e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
			e.getPlayer().sendMessage(DataManager.Config.getString("can_not_drop_item"));
			e.setCancelled(true);	
		}
		
	}
	
	@EventHandler
	public void placeBlock(BlockPlaceEvent e) {
		if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
		if(e.getPlayer().hasPermission("creative.res.bypass.placenondrop")) return;
		Block b = e.getBlock();
		

		if(!FileManager.locs.containsKey(b.getWorld().getName()))
			FileManager.read(b.getWorld());

		FileManager.locs.get(b.getWorld().getName()).add(b.getLocation().toVector());
//		e.getPlayer().sendMessage(""+FileManager.locs.get(b.getWorld().getName()).size());
	}
	
	@EventHandler
	public void breakBlock(BlockBreakEvent e) {
		Block b =e.getBlock() ;
		if(!FileManager.locs.containsKey(b.getWorld().getName()))
			FileManager.read(b.getWorld());
//		e.getPlayer().sendMessage(""+FileManager.locs.get(b.getWorld().getName()).size());
		if(FileManager.locs.get(b.getWorld().getName()).contains(b.getLocation().toVector())) {
			FileManager.locs.get(b.getWorld().getName()).remove(b.getLocation().toVector());
			
			e.setCancelled(true);
			b.setType(Material.AIR);
		}
	}
	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getClickedBlock()!=null) { 
			if(e.getClickedBlock().getState() instanceof Container&&!p.hasPermission("creative.res.opencontainer")) {
				e.setCancelled(true);
				p.sendMessage(DataManager.Config.getString("open_container"));
				return;
			}
			
		}
		
		if(e.getItem()==null) return;
		if(!p.hasPermission("creative.res.usespitem")&&p.getGameMode().equals(GameMode.CREATIVE)) {
			if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

			if(e.getItem().getType().toString().contains("SPAWN_EGG")) {
				p.sendMessage(DataManager.Config.getString("place_spawn_egg"));
				e.setCancelled(true);
				return;
			}
			if(Main.w != null)
				e.setCancelled(Main.w.onInteract(e.getItem().getType(), p));
		}
			
		
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChangeMode(PlayerGameModeChangeEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPermission("cmi.command.gm.*")) {
			String str = null;
			str = DataManager.Config.getString("chanage_gamemode_broadcast").replace("{player}", e.getPlayer().getDisplayName() == null ? e.getPlayer().getName() : e.getPlayer().getDisplayName());
			
			Bukkit.broadcast(str.replace("{gamemode}", TranslateGameMode(e.getNewGameMode())), "creative.res.show.gamemode.broadcast");
			
		}
		if(p.hasPermission("creative.res.bypass.invrecovery")) return;
		if(e.getNewGameMode().equals(GameMode.CREATIVE) || e.getNewGameMode().name().equalsIgnoreCase("CREATIVE")) {
			List<ItemStack> items = new ArrayList<ItemStack>();
			for(ItemStack is : p.getInventory().getStorageContents()) {
				items.add(is);
			}
			FileConfiguration pd = DataManager.getPlayerData(p);
			pd.set("LastInventory", items);
			DataManager.savePlayerData(p, pd);
		} else if(e.getPlayer().getGameMode().equals(GameMode.CREATIVE) || e.getPlayer().getGameMode().name().equalsIgnoreCase("CREATIVE")){
			FileConfiguration pd = DataManager.getPlayerData(p);
			p.getInventory().clear();
			int slot = 0;
			List<?> ls = pd.getList("LastInventory");
			if(ls != null)
				for(Object is : ls) {
					if(is!=null) 
						p.getInventory().setItem(slot, (ItemStack) is);	
						slot++;
				}
			
		}
	}
	
	public static String TranslateGameMode(GameMode e) {
		switch(e) {
		case ADVENTURE:
			return ChatColor.GOLD+ "adventure mode";
		case CREATIVE:
			return ChatColor.RED+ "creative mode";
		case SPECTATOR:
			return ChatColor.GRAY+ "spectator mode";
		case SURVIVAL:
			return ChatColor.GREEN+ "survival mode";
		}
		return null;
	}
	
	@EventHandler
	public void onWorldUnload(WorldUnloadEvent e) {
		FileManager.write(e.getWorld());
	}
}
