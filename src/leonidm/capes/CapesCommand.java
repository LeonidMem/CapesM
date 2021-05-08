package leonidm.capes;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CapesCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) { sender.sendMessage(CapesMain.getInstance().configString("not_player")); return true; }
		if(args.length == 0) { sender.sendMessage(CapesMain.getInstance().configString("usage")); return true; }
		Player p = (Player) sender;
		switch(args[0]) {
		
		case "set":
			if(!p.hasPermission("capes.set")) { sender.sendMessage(CapesMain.getInstance().configString("dont_have_permission")); break; };
			ItemStack is = p.getInventory().getItemInMainHand();
			if(is == null || !is.getType().toString().contains("BANNER")) { p.sendMessage(CapesMain.getInstance().configString("set.error")); break; }
			CapesAPI.setCape(p, is);
			p.sendMessage(CapesMain.getInstance().configString("set.ok"));
			break;
		
		case "remove":
			if(!p.hasPermission("capes.remove")) { sender.sendMessage(CapesMain.getInstance().configString("dont_have_permission")); break; };
			if(!CapesAPI.removeCape(p)) p.sendMessage(CapesMain.getInstance().configString("remove.error"));
			else p.sendMessage(CapesMain.getInstance().configString("remove.ok"));
			break;
		
		case "reload":
			if(!p.hasPermission("capes.reload")) { sender.sendMessage(CapesMain.getInstance().configString("dont_have_permission")); break; };
			CapesMain.getInstance().reloadConfig();
			p.sendMessage(CapesMain.getInstance().configString("config_reloaded"));
			break;
		
		default:
			p.sendMessage(CapesMain.getInstance().configString("usage"));
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cape")) {
			switch(args.length) {
			case 1:
				return Arrays.asList("set", "remove", "reload");
			default:
				return null;
			}
		}
		return null;
	}

}
