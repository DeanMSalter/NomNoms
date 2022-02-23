package McEssence.NomNoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;


public class Commands implements CommandExecutor {
    private final Config config;
    private final Main main;
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("NomNoms");
    public Commands(Config configTemp, Main mainTemp){
        config = configTemp;
        main = mainTemp;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args == null || args.length == 0) {
            return false;
        }
        switch(args[0].toUpperCase()) {
            case "RELOAD":
                if (hasPermission(commandSender, "NomNoms.admin.reload", true)) {
                    reload(commandSender, command, label, args);
                }
                break;
            case "SETHUNGER":
                if (hasPermission(commandSender, "NomNoms.admin.setHunger", true)) {
                    setHunger(commandSender, command, label, args);
                }
                break;
            case "SETSATURATION":
                if (hasPermission(commandSender, "NomNoms.admin.setSaturation", true)) {
                    setSaturation(commandSender, command, label, args);
                }
                break;
            case "GETHUNGER":
                if (hasPermission(commandSender, "NomNoms.admin.getHunger", true)) {
                    getHunger(commandSender, command, label, args);
                }
                break;
            case "GETSATURATION":
                if (hasPermission(commandSender, "NomNoms.admin.getSaturation", true)) {
                    getSaturation(commandSender, command, label, args);
                }
                break;
            default:
                break;
        }
        return true;
    }
    private Boolean reload(CommandSender commandSender, Command command, String s, String[] args){
        plugin.reloadConfig();
        commandSender.sendMessage("Reload Complete");
        return true;
    }
    private Boolean setHunger(CommandSender commandSender, Command command, String s, String[] args){
        try {
            if (!checkPlayerNameSupplied(args)){
                commandSender.sendMessage("No player name supplied");
                return true;
            }

            Player playerToSet = Bukkit.getServer().getPlayer(args[1]);

            if (playerToSet == null) {
                commandSender.sendMessage("Player not found");
                return true;
            }

            if (args[2] == null) {
                commandSender.sendMessage("Hunger not provided");
                return true;
            }

            playerToSet.setFoodLevel(Integer.parseInt(args[2]));
            return true;
        }catch(Exception e) {
            Bukkit.getLogger().info(ChatColor.RED + "Exception " + e.getMessage());
            return false;
        }
    }

    private Boolean setSaturation(CommandSender commandSender, Command command, String s, String[] args){
        try {
            if (!checkPlayerNameSupplied(args)){
                commandSender.sendMessage("No player name supplied");
                return true;
            }

            Player playerToSet = Bukkit.getServer().getPlayer(args[1]);

            if (playerToSet == null) {
                commandSender.sendMessage("Player not found");
                return true;
            }

            if (args[2] == null) {
                commandSender.sendMessage("Saturation not provided");
                return true;
            }

            playerToSet.setSaturation(Integer.parseInt(args[2]));
            return true;
        }catch(Exception e) {
            Bukkit.getLogger().info(ChatColor.RED + "Exception " + e.getMessage());
            return false;
        }
    }

    private Boolean getHunger(CommandSender commandSender, Command command, String s, String[] args){
        try {
            if (!checkPlayerNameSupplied(args)){
                commandSender.sendMessage("No player name supplied");
                return true;
            }

            Player playerToSet = Bukkit.getServer().getPlayer(args[1]);

            if (playerToSet == null) {
                commandSender.sendMessage("Player not found");
                return true;
            }
            commandSender.sendMessage(String.valueOf(playerToSet.getFoodLevel()));
            return true;
        }catch(Exception e) {
            Bukkit.getLogger().info(ChatColor.RED + "Exception " + e.getMessage());
            return false;
        }
    }

    private Boolean getSaturation(CommandSender commandSender, Command command, String s, String[] args){
        try {
            if (!checkPlayerNameSupplied(args)){
                commandSender.sendMessage("No player name supplied");
                return true;
            }

            Player playerToSet = Bukkit.getServer().getPlayer(args[1]);

            if (playerToSet == null) {
                commandSender.sendMessage("Player not found");
                return true;
            }
            commandSender.sendMessage(String.valueOf(playerToSet.getSaturation()));
            return true;
        }catch(Exception e) {
            Bukkit.getLogger().info(ChatColor.RED + "Exception " + e.getMessage());
            return false;
        }
    }

    private Boolean checkPlayerRan(CommandSender commandSender){
        return commandSender instanceof Player;
    }
    private Boolean checkPlayerNameSupplied(String[] args){
        return args != null && args[1] != null;
    }
    private Boolean hasPermission(CommandSender commandSender, String permission, boolean allowConsole) {
        if (!(commandSender instanceof Player) && !allowConsole) {
            commandSender.sendMessage("This command can not be run from the console.");
            return false;
        }else if(!(commandSender instanceof Player)) {
            return true;
        }
        Player player = (Player) commandSender;
        if (player.hasPermission(permission)) {
            return true;
        }else {
            commandSender.sendMessage("You do not have permission.");
        }
        return false;
    }
}

