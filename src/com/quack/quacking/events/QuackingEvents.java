package com.quack.quacking.events;

import com.quack.quacking.Quacking;
import com.quack.quacking.items.ItemManager;
import org.bukkit.*;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import com.quack.quacking.files.DataManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.quack.quacking.events.StartFuelChecker.fuelCheckerScheduler;
import static com.quack.quacking.events.StartFuelChecker.fuelCheckerTask;
import static org.bukkit.Bukkit.getServer;


public class QuackingEvents implements Listener {

    public static DataManager dataIncoming;

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR) { // || event.getAction() == Action.RIGHT_CLICK_BLOCK
            if(event.getItem() != null) { //make sure there is no error if nothing in hand
                //sticks' functions go here
                Player player = event.getPlayer();
                //explodingWand
                if(event.getItem().getItemMeta().equals(ItemManager.explodingWand.getItemMeta())) {

                    player.getWorld().createExplosion(player.getLocation(), 300.0f); //perhaps too large. some might consider this a nuke :)
                    player.sendMessage("§4boom_stick");
                }
                //flyStick
                if(event.getItem().getItemMeta().equals(ItemManager.flyStick.getItemMeta())) {
                    //check for hunger
                    if(player.getFoodLevel() > 0) {
                        player.setFoodLevel(player.getFoodLevel() - 1);
                        player.setVelocity(player.getLocation().getDirection().multiply(3));
                    } else {
                        //take away half a heart per click
                        if(player.getHealth() > 1) {
                            player.setHealth(player.getHealth() - 1);
                            player.setVelocity(player.getLocation().getDirection().multiply(3));
                        } else {
                            player.sendMessage("§d[Quacking] §4You are gonna die if you try again.");
                        }
                    }
                }

                //toggleFlightStick

                if(event.getItem().getItemMeta().equals(ItemManager.flightToggleStick.getItemMeta())) {
                    DataManager data = dataIncoming;
                    //if they have flight
                    if(player.getAllowFlight()) {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.setFlySpeed(0.1f);
                        //set data type to 0 (false) for quackingAllowFlight
                        player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER, 0);
                        player.sendMessage("§d[Quacking] §bFlight toggled §c§lOFF");
                    } else if(!player.getAllowFlight()) {

                        String fuelBlock = "GOLD_BLOCK"; //default
                        if(data.getConfig().contains("fuelBlock"))
                            fuelBlock = data.getConfig().getString("fuelBlock");

                        Material fuelBlockMat = Material.getMaterial(fuelBlock);
                        if(!fuelBlockMat.isItem()) {
                            fuelBlockMat = Material.GOLD_BLOCK;
                        }
                        if(!player.getInventory().contains(fuelBlockMat)) {
                            player.sendMessage("§d[Quacking] §bYou don't have §a" + fuelBlockMat.name() + " §bto fly with");
                            return;
                        }
                        //set data type to 1 (true) for quackingAllowFlight
                        player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER, 1);
                        if(player.getPersistentDataContainer().get(new NamespacedKey(Quacking.getPlugin(), "quackingJustStartedFlying"), PersistentDataType.INTEGER) == 0) {
                            player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingJustStartedFlying"), PersistentDataType.INTEGER, 1);
                        }

                        //cancel it, and restart it whenever someone uses it. pretty shit but might work
                        fuelCheckerScheduler.cancelTask(fuelCheckerTask);
                        new StartFuelChecker().fuelChecker(new DataManager(Quacking.getPlugin()));

                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.setFlySpeed(0.040f);
                        player.sendMessage("§d[Quacking] §bFlight toggled §a§lON");

                    }
                }
                //sticks' end here
            }

        }


    }//event onRightClick

    //flight global
    public static void checkAllPlayerFuel() {

        DataManager data = dataIncoming;
        if(data == null) Quacking.getPlugin().getServer().broadcastMessage("Flight Checker broke :[");

        Server server = Quacking.getPlugin().getServer();
        //server.broadcastMessage("§d[Quacking] §bhello");
        for(Player player : server.getOnlinePlayers()) {

            if(!player.getAllowFlight()) return;
            if(player.getGameMode() == GameMode.CREATIVE) return;

            if(player.isFlying() && player.getPersistentDataContainer().get(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER) == 1) {
                //do something if they are flying with the special dataContainer.
                //player.sendMessage("your name is " + player.getName());//debug
                //quackingJustStartedFlying if player just started flying, ignore it.
                /*if(player.getPersistentDataContainer().get(new NamespacedKey(Quacking.getPlugin(), "quackingJustStartedFlying"), PersistentDataType.INTEGER) == 1) {
                    player.sendMessage("You just started flying.");
                    player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingJustStartedFlying"), PersistentDataType.INTEGER, 0);
                    return;
                }*/

                //check if they have the fucking hoe. holy shit this should've been in here from the beginning
                if(!player.getInventory().contains(ItemManager.flightToggleStick)) {
                    player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER, 0);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setFlySpeed(0.1f);
                    player.sendMessage("§d[Quacking] §bYou don't have the §dflight_toggle_hoe §bin your inventory.");
                    player.sendMessage("§d[Quacking] §bFlight toggled §c§lOFF");
                    return;
                }

                //take away fuel here.
                String fuelBlock = "GOLD_BLOCK"; //default
                if(data.getConfig().contains("fuelBlock"))
                    fuelBlock = data.getConfig().getString("fuelBlock");

                Material fuelBlockMat = Material.getMaterial(fuelBlock);
                if(!fuelBlockMat.isItem()) {
                    fuelBlockMat = Material.GOLD_BLOCK;
                }

                if(!player.getInventory().contains(fuelBlockMat)) {
                    player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER, 0);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setFlySpeed(0.1f);
                    player.sendMessage("§d[Quacking] §bOut of §cfuel §bto fly with. §c§lL");
                    return;
                }

                int count;
                count = 0;
                for (ItemStack stack : player.getInventory().getContents()) {
                    if (stack != null && stack.getType() == fuelBlockMat) {
                        count += stack.getAmount();
                    }
                }
                count--;
                player.getInventory().removeItem(new ItemStack(fuelBlockMat, 1));
                //add colors for messages
                if(count >= 100) {
                    player.sendMessage("§c§l-1 fuel (§a§l" + count + "§c§l)");
                } else if(count >= 10 && count <= 100) {
                    player.sendMessage("§c§l-1 fuel (§e§l" + count + "§c§l)");
                } else {
                    player.sendMessage("§c§l-1 fuel (§4§l" + count + "§c§l)");
                }

                //player.sendMessage("§c§l-1 fuel (§a§l" + count + "§c§l)");

            }
        }

    }






    //on death for flightStick
    @EventHandler
    public static void onDeathFlightStick(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity().getPlayer();

        assert player != null;
        if(player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if(player.getAllowFlight() && Objects.equals(player.getPersistentDataContainer().get(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER), 1)) {
            player.sendMessage("§dyou lost your flight boots because you died.");
            player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER, 0);
            player.setFlySpeed(0.1f);
        }
        //player.sendMessage("you died lol");

    }

}
