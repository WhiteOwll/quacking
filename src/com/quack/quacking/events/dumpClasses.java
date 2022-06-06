package com.quack.quacking.events;

import com.quack.quacking.Quacking;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class dumpClasses implements Listener {
    //make it so emeralds (fuel) goes down every so often. This event doesn't work cus player is technically flying and not on ground.
    @EventHandler
    public static void onMove(Player event) {
        //move event
        //for Flight fuel
        Player player = event.getPlayer();
        //player.sendMessage("§d[Quacking] §bHello ");
        if(player.isFlying()) {
            if(player.getGameMode() == GameMode.CREATIVE) {
                return;
            }
            if(player.getPersistentDataContainer().get(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER) == 1) {

                if(!player.getInventory().contains(Material.EMERALD)) {
                    player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER, 0);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendMessage("§d[Quacking] §bOut of §aEmeralds §bto fly with. L");
                    return;
                }

                int count = 0;
                for (ItemStack stack : player.getInventory().getContents()) {
                    if (stack != null && stack.getType() == Material.EMERALD) {
                        count += stack.getAmount();
                    }
                }
                player.getInventory().removeItem(new ItemStack(Material.EMERALD, 1));
                player.sendMessage("§d[Quacking] §bYou have " + count + " emeralds left");

            }
        }

    }
}
