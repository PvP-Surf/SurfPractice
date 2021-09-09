package surf.pvp.practice.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class TimerTask {

    private int time;
    private final JavaPlugin plugin;

    private BukkitTask task;
    private BukkitTask cancelTask;

    public TimerTask(JavaPlugin plugin, int time) {
        this.time = time;
        this.plugin = plugin;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return this.time;
    }

    public boolean stillRunning() {
        return plugin.getServer().getScheduler().isCurrentlyRunning(task.getTaskId());
    }

    public void start() {
        task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> time--, 20L, 20L);
        cancelTask = plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> plugin.getServer().getScheduler().cancelTask(task.getTaskId()), time * 20L);
    }

    public void cancel() {
        if (task == null || cancelTask == null) return;
        plugin.getServer().getScheduler().cancelTask(task.getTaskId());
        plugin.getServer().getScheduler().cancelTask(cancelTask.getTaskId());
    }

    public boolean hasCompleted() {
        return time == 0;
    }

}
