package McEssence.NomNoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    Config config;

    @Override
    public void onEnable() {
        File f = new File(this.getDataFolder() + "/");
        if(!f.exists()) {
            f.mkdir();
        }
        getConfig().options().copyDefaults(true);
        saveConfig();

        config = new Config(this);
        if (!config.getEnabled()){
            Bukkit.getLogger().info(ChatColor.RED + " Disabled" + this.getName() + " As not enabled in config");
            return;
        }
        this.getCommand("NomNoms").setExecutor(new Commands(config, this));
        getServer().getPluginManager().registerEvents(new Listeners(config), this);
    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Disabled " + this.getName());
    }

}
