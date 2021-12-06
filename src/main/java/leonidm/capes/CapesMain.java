package leonidm.capes;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

public class CapesMain extends JavaPlugin {

    private static CapesMain instance;

    @Override
    public void onEnable() {
        instance = this;
        if(!new File("plugins/CapesM/config.yml").exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        Bukkit.getPluginManager().registerEvents(new CapesBehaviour(), this);
        getCommand("cape").setExecutor(new CapesCommand());
        getLogger().info("Enabled!");
    }

    @Override
    public void onDisable() {
        for(Entry<String, ArmorStand> entry : CapesAPI.capes.entrySet()) entry.getValue().remove();
        getLogger().info("Disabled!");
    }

    public String configString(String path) {
        Object i = getConfig().get(path);
        if(i == null) return path;
        if(i instanceof String) return ChatColor.translateAlternateColorCodes('&', (String) i);
        if(i instanceof List<?>) {
            StringBuilder sb = new StringBuilder();
            for(Object j : (List<?>) i) {
                sb.append(j);
                sb.append("\n");
            }
            return ChatColor.translateAlternateColorCodes('&', sb.toString());
        }
        return i.toString();
    }

    public static CapesMain getInstance() {
        return instance;
    }
}
