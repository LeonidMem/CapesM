package leonidm.capes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class CapesCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)) { sender.sendMessage(CapesMain.getInstance().configString("not_player")); return true; }
        if(args.length == 0) { sender.sendMessage(CapesMain.getInstance().configString("usage")); return true; }
        Player p = (Player) sender;
        switch(args[0]) {

            case "set":
                if(!p.hasPermission("capes.set")) { sender.sendMessage(CapesMain.getInstance().configString("dont_have_permission")); break; };
                ItemStack is = p.getInventory().getItemInMainHand();
                if(is == null || !is.getType().toString().contains("BANNER")) { p.sendMessage(CapesMain.getInstance().configString("set.error")); break; }
                CapesAPI.setCape(p, is);

                ArmorStand as = CapesAPI.capes.get(sender.getName());
                if(((Player) sender).isSneaking()) {
                    as.setHeadPose(new EulerAngle(-2.5, 0 ,0));
                    as.teleport(((Player) sender).getLocation().clone().add(0, 0.8, 0));
                }
                else {
                    double yaw = Math.toRadians(((Player) sender).getLocation().getYaw());
                    double sin = Math.sin(yaw);
                    double cos = Math.cos(yaw);

                    as.teleport(((Player) sender).getLocation().clone().add(sin / 4, 1, -cos / 4));
                    as.setHeadPose(new EulerAngle(-3, 0 ,0));
                }

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
    public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("cape")) {
            if (args.length == 1) {
                return Stream.of("set", "remove", "reload").filter(s -> s.startsWith(args[0].toLowerCase(Locale.ENGLISH))).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

}
