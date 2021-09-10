package surf.pvp.practice.listener.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import surf.pvp.practice.listener.events.impl.global.PracticeDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public final void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if (!(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        if (event.getFinalDamage() < target.getHealth())
            return;

        final PracticeDeathEvent practiceDeathEvent = new PracticeDeathEvent(player, target, event.getFinalDamage());
        Bukkit.getPluginManager().callEvent(practiceDeathEvent);

        event.setDamage(practiceDeathEvent.getDamage());

        if (practiceDeathEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public final void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK))
            return;

        if (!(event.getEntity() instanceof Player))
            return;

        Player target = (Player) event.getEntity();

        if (event.getFinalDamage() < target.getHealth())
            return;

        final PracticeDeathEvent practiceDeathEvent = new PracticeDeathEvent(null, target, event.getFinalDamage());
        Bukkit.getPluginManager().callEvent(practiceDeathEvent);

        event.setDamage(practiceDeathEvent.getDamage());

        if (practiceDeathEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

}
