package creative.dru.org;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("only player can do this!");
			return true;
		}
		Player p = (Player) sender;
		p.chat("/msg shiunchen 有人叫你");
		return true;
	}

}
