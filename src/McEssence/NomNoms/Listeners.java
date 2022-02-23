package McEssence.NomNoms;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Listeners implements Listener {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("NomNoms");
    private final Config config;
    public Listeners(Config configTemp){
        config = configTemp;
    }

    ItemStack bowl = new ItemStack(Material.BOWL);
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Material food = item.getType();
        if (config.getConfiguredFoods().contains(item.getType())) {
            event.setCancelled(true);

            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            player.setFoodLevel(player.getFoodLevel() + config.getHunger(food));
            player.setSaturation(config.getSaturation(food));

            if (config.getGiveBowl(food)) {
                player.getInventory().addItem(bowl);
            }
        }
    }

    @EventHandler
    public void PickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            setItemLore(event.getItem().getItemStack());
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event) {
        InventoryType playerInventory = InventoryType.CRAFTING;
        if (event.getInventory().getType() == playerInventory) {
            setItemLore(event.getCursor());
        }
    }

    private void setItemLore(ItemStack itemToChange){
        ItemMeta meta = itemToChange.getItemMeta();
        List<String> Lore = new ArrayList<>();
        Lore.add("Hunger: " + config.getHunger(itemToChange.getType()));
        Lore.add("Saturation: " + config.getSaturation(itemToChange.getType()));

        meta.setLore(Lore);
        itemToChange.setItemMeta(meta);
    }
}
