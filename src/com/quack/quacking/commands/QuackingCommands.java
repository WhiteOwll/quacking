package com.quack.quacking.commands;

import com.quack.quacking.Quacking;
import com.quack.quacking.events.StartFuelChecker;
import com.quack.quacking.files.DataManager;
import com.quack.quacking.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.quack.quacking.events.StartFuelChecker.fuelCheckerScheduler;
import static com.quack.quacking.events.StartFuelChecker.fuelCheckerTask;

public class QuackingCommands implements CommandExecutor {

    public static DataManager dataIncoming;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Only players can use that command.");
            return true;
        }
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("snowball")) {
            //player.getInventory().addItem(ItemManager.snowBallCannon);
            player.sendMessage("§dSnowball cannon (soon)");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("refreshfuel")) {
            if(!player.isOp()) {
                player.sendMessage("§cYou are missing permissions for this command. You need to have op.");
                return true;
            }
            fuelCheckerScheduler.cancelTask(fuelCheckerTask);
            //DataManager data = dataIncoming;
            //if(data == null) return true;
            player.sendMessage("§aRefreshed fuelchecker");
            new StartFuelChecker().fuelChecker(new DataManager(Quacking.getPlugin()));

            return true;
        }

        if(cmd.getName().equalsIgnoreCase("flight_stick_info")) {
            DataManager data = Quacking.getPlugin().data;
            if(data == null) return true;

            String fuelBlock = "GOLD_BLOCK"; //default
            int fuelTime = 100;
            if(data.getConfig().contains("fuelBlock"))
                fuelBlock = data.getConfig().getString("fuelBlock");
            if(data.getConfig().contains("fuelTime"))
                fuelTime = data.getConfig().getInt("fuelTime");

            Material fuelBlockMat = Material.getMaterial(fuelBlock);
            if(!fuelBlockMat.isItem()) {
                fuelBlockMat = Material.GOLD_BLOCK;
            }

            player.sendMessage("§dtoggle_flight_hoe §acurrently uses §e" + fuelBlockMat + " §aas fuel.");
            player.sendMessage("§e(1 fuel/" + fuelTime + " sec)");
            player.sendMessage("§aObtained by smelting a §e§lTotem of undying");
        }

        if(cmd.getName().equalsIgnoreCase("cyastick")) {
            if(!player.isOp()) {
                return true;
            }
            player.getInventory().addItem(ItemManager.cyaStick);
            player.sendMessage("§4the cya stick");
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("flystick")) {
            if(!(player.getInventory().contains(Material.NETHERITE_BLOCK))) {
                player.sendMessage("§4You need a §d§lNetherite Block.");
                return true;
            }
            player.getInventory().removeItem(new ItemStack(Material.NETHERITE_BLOCK, 1));
            player.getInventory().addItem(ItemManager.flyStick);
            player.sendMessage("§dEnjoy the fly_stick. Right click to fly");
            player.sendMessage("§c§l!! Uses hunger!!");
            return true;
        }


        return true;
    }
}
