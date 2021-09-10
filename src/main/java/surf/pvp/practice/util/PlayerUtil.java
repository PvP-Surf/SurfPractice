package surf.pvp.practice.util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class PlayerUtil {


    public static void reset(Player player) {
        reset(player, false);
    }

    public static void reset(Player player, boolean resetHeldSlot) {
        player.getActivePotionEffects().clear();
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0f);
        player.setFireTicks(0);
        player.setMaximumNoDamageTicks(20);
        player.setNoDamageTicks(20);
        player.setSaturation(20);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.getInventory().setContents(new ItemStack[36]);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.updateInventory();
    }

    public static void denyMovement(Player player) {
        player.setWalkSpeed(0.0F);
        player.setFlySpeed(0.0F);
        player.setFoodLevel(0);
        player.setSprinting(false);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
    }

    public static void allowMovement(Player player) {
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.2F);
        player.setFoodLevel(20);
        player.setSprinting(true);
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    public static void removeItems(Inventory inventory, ItemStack item, int amount) {
        for (int size = inventory.getSize(), slot = 0; slot < size; ++slot) {
            ItemStack is = inventory.getItem(slot);
            if (is != null && item.getType() == is.getType() && item.getDurability() == is.getDurability()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                } else {
                    inventory.setItem(slot, new ItemStack(Material.AIR));
                    amount = -newAmount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
    }

    public static List<Player> convertUUIDListToPlayerList(List<UUID> list) {
        return list.stream().map(Bukkit::getPlayer).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static Player getPlayer(String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return Bukkit.getPlayer(name);
    }

    public static Player getLastAttacker(Player victim) {
        if (victim.hasMetadata("lastAttacker")) {
            return Bukkit.getPlayer((UUID) victim.getMetadata("lastAttacker").get(0).value());
        } else {
            return null;
        }
    }

    public static void applyPermissions(JavaPlugin javaPlugin, Player player, List<String> permissions, boolean async) {

        if (async) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> applyPermissions(javaPlugin, player, permissions, false));
            return;
        }

        clearPermissions(javaPlugin, player, false);

        permissions.forEach(permission -> {
            PermissionAttachment permissionAttachment = player.addAttachment(javaPlugin);
            permissionAttachment.setPermission(permission, true);
        });

        player.recalculatePermissions();
    }

    public static void clearPermissions(JavaPlugin javaPlugin, Player player, boolean async) {

        if (async) {
            javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> clearPermissions(javaPlugin, player, false));
            return;
        }

        player.getEffectivePermissions().forEach(permissionAttachmentInfo ->
                player.removeAttachment(permissionAttachmentInfo.getAttachment()));

        player.recalculatePermissions();
    }
}
