package McEssence.NomNoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.configuration.file.YamlConfiguration.*;

public class Config{
    private final Main main;
    public Config(Main mainTemp){
        main = mainTemp;
    }

    public Boolean getEnabled(){
        return main.getConfig().getBoolean("general.enabled");
    }
    public String getCanNotBreak(){
        return main.getConfig().getString("messages.canNotBreak");
    }
    public String getCanNotOpen(){
        return main.getConfig().getString("messages.canNotOpen");
    }

    public ArrayList<Material> getConfiguredFoods(){
        ArrayList<Material> foodMaterialList = new ArrayList<>();
        try{
            ConfigurationSection foods = main.getConfig().getConfigurationSection("foods");
            for (String foodName : foods.getKeys(false)) {
                foodMaterialList.add(Material.getMaterial(foodName));
            }
        }catch(Exception e){
            Bukkit.getLogger().info(ChatColor.RED + "Error occured when getting excluded blocks");
        }
        return foodMaterialList;
    }
    public Float getSaturation(Material foodName) {
        ConfigurationSection foods = main.getConfig().getConfigurationSection("foods");
        ConfigurationSection food = foods.getConfigurationSection(foodName.toString());
        return (float) food.getDouble("saturation");
    }
    public Integer getHunger(Material foodName) {
        ConfigurationSection foods = main.getConfig().getConfigurationSection("foods");
        ConfigurationSection food = foods.getConfigurationSection(foodName.toString());
        return food.getInt("hunger");
    }
    public Boolean getGiveBowl(Material foodName) {
        ConfigurationSection foods = main.getConfig().getConfigurationSection("foods");
        ConfigurationSection food = foods.getConfigurationSection(foodName.toString());
        return food.getBoolean("giveBowl");
    }
}
