package leonidm.capes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.Nullable;

public class CapesAPI {

    final static Map<String, ArmorStand> capes = new HashMap<>();

    /**
     * Set given ItemStack as a Player's cape
     * @param player
     * @param banner
     */
    public static void setCape(Player player, ItemStack banner) {
        removeCape(player);
        ArmorStand as = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        if(as.getEquipment() != null)
            as.getEquipment().setHelmet(banner);
        as.setVisible(false);
        as.setMarker(true);
        as.setHeadPose(new EulerAngle(-3, 0 ,0));
        as.setSmall(true);
        as.addScoreboardTag("leonidm.capes");
        as.setGravity(false);
        capes.put(player.getName(), as);
    }

    /**
     * Remove Player's cape
     * @param player
     * @return Returns "true" if cape was removed, "false" if not
     */
    public static boolean removeCape(Player player) {
        ArmorStand as = capes.remove(player.getName());
        if(as == null) return false;
        as.remove();
        return true;
    }

    /**
     * Check if Player has any cape
     * @param player
     * @return Returns "true" if Player has any cape, "false" if not
     */
    public static boolean hasCape(Player player) {
        if(capes.get(player.getName()) == null) return false;
        return true;
    }

    /**
     * Get Player's cape
     * @param player
     * @return Returns ItemStack if Player has any cape, null if not
     */
    @Nullable
    public static ItemStack getCape(Player player) {
        ArmorStand as = capes.get(player.getName());
        if(as == null) return null;
        return as.getEquipment().getHelmet();
    }

}
