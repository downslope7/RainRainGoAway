package haus.slope.rainraingoaway;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by downslope7 on 2/7/15.
 */
public final class RainRainGoAway extends JavaPlugin implements Listener {
    private FileConfiguration config;
    
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        config = getConfig();
    }
    
    @Override
    public void onDisable() {
        getLogger().info("Going Away");
    }
    
    public boolean isRainDisabledInWorld(String worldName) {
        return config.getBoolean("noRainEverywhere") || config.getList("onlyTheseWorlds").contains(worldName);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onRainStart(WeatherChangeEvent event) {
        if(!event.isCancelled()) {
            String worldName = event.getWorld().getName();
            boolean rainStarting = event.toWeatherState();
            getLogger().info("Rain state for world " + worldName + " switching to " + rainStarting);
            if(rainStarting && isRainDisabledInWorld(worldName)) {
                event.setCancelled(true);
                getLogger().info("Rain cancelled in world: " + worldName);
            }
        }
    }
}
