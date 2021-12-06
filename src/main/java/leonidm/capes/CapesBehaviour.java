package leonidm.capes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class CapesBehaviour implements Listener {

    private final static Map<String, ItemStack> removedCapes = new HashMap<>();

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        CapesAPI.removeCape(e.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        ArmorStand as = CapesAPI.capes.get(e.getPlayer().getName());
        if(as == null) return;

        double yaw = Math.toRadians(e.getPlayer().getLocation().getYaw());
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);

        if(e.getPlayer().isSneaking()) as.teleport(e.getPlayer().getLocation().clone().add(0, 0.8, 0));
        else as.teleport(e.getPlayer().getLocation().clone().add(sin / 4, 1, -cos / 4));
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        ArmorStand as = CapesAPI.capes.get(e.getPlayer().getName());
        if(as == null) return;

        if(e.isSneaking()) {
            as.setHeadPose(new EulerAngle(-2.5, 0 ,0));
            as.teleport(e.getPlayer().getLocation().clone().add(0, 0.8, 0));
            return;
        }

        double yaw = Math.toRadians(e.getPlayer().getLocation().getYaw());
        double sin = Math.sin(yaw);
        double cos = Math.cos(yaw);

        as.teleport(e.getPlayer().getLocation().clone().add(sin / 4, 1, -cos / 4));
        as.setHeadPose(new EulerAngle(-3, 0 ,0));
    }

    @EventHandler
    public void onChangeGamemode(PlayerGameModeChangeEvent e) {
        if(e.getNewGameMode() == GameMode.SPECTATOR && CapesAPI.capes.containsKey(e.getPlayer().getName())) {

            if(removedCapes.containsKey(e.getPlayer().getName())) return;

            ArmorStand as = CapesAPI.capes.get(e.getPlayer().getName());
            if(as == null) return;

            removedCapes.put(e.getPlayer().getName(), as.getEquipment().getHelmet().clone());
            as.getEquipment().setHelmet(new ItemStack(Material.AIR));
            return;
        }
        if(e.getNewGameMode() != GameMode.SPECTATOR && removedCapes.containsKey(e.getPlayer().getName())) {
            ArmorStand as = CapesAPI.capes.get(e.getPlayer().getName());
            as.getEquipment().setHelmet(removedCapes.remove(e.getPlayer().getName()));
        }
    }

    @EventHandler
    public void onEffect(EntityPotionEffectEvent e) {
        if(e.getEntityType() != EntityType.PLAYER) return;

        if(e.getNewEffect() != null && e.getNewEffect().getType() == PotionEffectType.INVISIBILITY) {

            if(removedCapes.containsKey(e.getEntity().getName())) return;

            ArmorStand as = CapesAPI.capes.get(e.getEntity().getName());
            if(as == null) return;

            removedCapes.put(e.getEntity().getName(), as.getEquipment().getHelmet().clone());
            as.getEquipment().setHelmet(new ItemStack(Material.AIR));
            return;
        }

        if(e.getOldEffect() != null && e.getOldEffect().getType() == PotionEffectType.INVISIBILITY) {
            ArmorStand as = CapesAPI.capes.get(e.getEntity().getName());
            as.getEquipment().setHelmet(removedCapes.remove(e.getEntity().getName()));
        }

    }
}
