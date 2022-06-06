package com.quack.quacking.events;

import com.quack.quacking.Quacking;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class PersistDataStorage implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if((player.getAllowFlight() || player.isFlying()) && player.getGameMode() != GameMode.CREATIVE) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
        player.setFlySpeed(0.1f);
        //add "quackingAllowFlight": 0 to player if they don't have it already.
        if(!player.getPersistentDataContainer().has(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER)) {
            player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingAllowFlight"), PersistentDataType.INTEGER, 0);
        }
        //for flightStick
        if(!player.getPersistentDataContainer().has(new NamespacedKey(Quacking.getPlugin(), "quackingJustStartedFlying"), PersistentDataType.INTEGER)) {
            player.getPersistentDataContainer().set(new NamespacedKey(Quacking.getPlugin(), "quackingJustStartedFlying"), PersistentDataType.INTEGER, 0);
        }
    }
}
