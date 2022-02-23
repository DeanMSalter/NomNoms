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

import static java.util.Map.entry;
import static org.bukkit.configuration.file.YamlConfiguration.*;

public class Config{
    private final Main main;
    public Config(Main mainTemp){
        main = mainTemp;
    }
    Map<String, Integer> foodHunger = Map.ofEntries(
            entry("RABBIT_STEW", 10),
            entry("COOKED_PORKCHOP", 8),
            entry("COOKED_BEEF", 8),
            entry("PUMPKIN_PIE", 8),
            entry("GOLDEN_CARROT", 6),
            entry("COOKED_MUTTON", 6),
            entry("COOKED_SALMON", 6),
            entry("BEETROOT_SOUP", 6),
            entry("COOKED_CHICKEN", 6),
            entry("MUSHROOM_STEW", 6),
            entry("SUSPICIOUS_STEW", 6),
            entry("HONEY_BOTTLE", 6),
            entry("BAKED_POTATO", 5),
            entry("BREAD", 5),
            entry("COOKED_COD", 5),
            entry("COOKED_RABBIT", 5),
            entry("ENCHANTED_GOLDEN_APPLE", 4),
            entry("GOLDEN_APPLE", 4),
            entry("APPLE", 4),
            entry("ROTTEN_FLESH", 4),
            entry("CARROT", 3),
            entry("BEEF", 3),
            entry("PORKCHOP", 3),
            entry("RABBIT", 3),
            entry("SWEET_BERRIES", 3),
            entry("SPIDER_EYE", 2),
            entry("MELON_SLICE", 2),
            entry("POISONOUS_POTATO", 2),
            entry("CHICKEN", 2),
            entry("MUTTON", 2),
            entry("COOKIE", 2),
            entry("GLOW_BERRIES", 2),
            entry("RAW_COD", 2),
            entry("RAW_SALMON", 2),
            entry("DRIED_KELP", 1),
            entry("BEETROOT", 1),
            entry("POTATO", 1),
            entry("PUFFERFISH", 1),
            entry("TROPICAL_FISH", 1));
    Map<String, Double> foodSaturation = Map.ofEntries(
            entry("RABBIT_STEW", 12.0),
            entry("COOKED_PORKCHOP", 12.8),
            entry("COOKED_BEEF", 12.8),
            entry("PUMPKIN_PIE", 4.8),
            entry("GOLDEN_CARROT", 14.4),
            entry("COOKED_MUTTON", 9.6),
            entry("COOKED_SALMON", 9.6),
            entry("BEETROOT_SOUP", 7.2),
            entry("COOKED_CHICKEN", 7.2),
            entry("MUSHROOM_STEW", 7.2),
            entry("SUSPICIOUS_STEW", 7.2),
            entry("HONEY_BOTTLE", 1.2),
            entry("BAKED_POTATO", 6.0),
            entry("BREAD", 6.0),
            entry("COOKED_COD", 6.0),
            entry("COOKED_RABBIT", 6.0),
            entry("ENCHANTED_GOLDEN_APPLE", 9.6),
            entry("GOLDEN_APPLE", 9.6),
            entry("APPLE", 2.4),
            entry("ROTTEN_FLESH", 0.8),
            entry("CARROT", 3.6),
            entry("BEEF", 1.8),
            entry("PORKCHOP", 1.8),
            entry("RABBIT", 1.8),
            entry("SWEET_BERRIES", 0.4),
            entry("SPIDER_EYE", 3.2),
            entry("MELON_SLICE", 1.2),
            entry("POISONOUS_POTATO", 1.2),
            entry("CHICKEN", 1.2),
            entry("MUTTON", 1.2),
            entry("COOKIE", 0.4),
            entry("GLOW_BERRIES", 0.4),
            entry("COD", 0.4),
            entry("SALMON", 0.4),
            entry("DRIED_KELP", 0.6),
            entry("BEETROOT", 1.2),
            entry("POTATO", 0.6),
            entry("PUFFERFISH", 0.2),
            entry("TROPICAL_FISH", 0.2));

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
        if (foodName == null) {
            return null;
        }
        if (IsConfiguredFood(foodName.toString())) {
            ConfigurationSection foods = main.getConfig().getConfigurationSection("foods");
            ConfigurationSection food = foods.getConfigurationSection(foodName.toString());
            return (float) food.getDouble("saturation");
        } else if(foodSaturation.containsKey(foodName.toString())) {
            return foodSaturation.get(foodName.toString()).floatValue();
        } else {
            return null;
        }


    }
    public Integer getHunger(Material foodName) {
        if (foodName == null) {
            return null;
        }
        if (IsConfiguredFood(foodName.toString())) {
            ConfigurationSection foods = main.getConfig().getConfigurationSection("foods");
            ConfigurationSection food = foods.getConfigurationSection(foodName.toString());
            return food.getInt("hunger");
        } else if(foodHunger.containsKey(foodName.toString())) {
            return foodHunger.get(foodName.toString());
        } else {
            return null;
        }
    }
    public Boolean getGiveBowl(Material foodName) {
        if (foodName == null) {
            return false;
        }
        if (!IsConfiguredFood(foodName.toString())) {
            return null;
        }
        ConfigurationSection foods = main.getConfig().getConfigurationSection("foods");
        ConfigurationSection food = foods.getConfigurationSection(foodName.toString());
        return food.getBoolean("giveBowl");
    }

    private boolean IsConfiguredFood(String foodName) {
        ConfigurationSection foods = main.getConfig().getConfigurationSection("foods");
        if (!foods.contains(String.valueOf(foodName))) {
            return false;
        }
        return true;
    }
}
