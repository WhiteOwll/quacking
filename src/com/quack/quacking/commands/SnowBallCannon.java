package com.quack.quacking.commands;

import com.quack.quacking.Quacking;
import com.quack.quacking.files.DataManager;
import com.quack.quacking.items.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;


public class SnowBallCannon implements Listener {


    public static DataManager dataIncoming;

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) { // || event.getAction() == Action.RIGHT_CLICK_BLOCK
            if (event.getItem() != null) { //make sure there is no error if nothing in hand
                Player player = event.getPlayer();
                //snowball cannon
                if(Objects.equals(Objects.requireNonNull(event.getItem()).getItemMeta(), ItemManager.snowBallCannon.getItemMeta())) {
                    Snowball snowball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
                    snowball.setCustomNameVisible(false);
                    snowball.setCustomName("snow ball");
                    snowball.setVelocity(player.getLocation().getDirection().multiply(1.5));
                    snowball.setShooter(player);
                    snowball.setGravity(false);
                    snowball.setGlowing(true);
                    snowBallDelete(snowball);

                    player.sendMessage("§d[Quacking] §bsnowball"); //debug

                }
            }
        }
    }

    @EventHandler
    public static void onSnowBallHit(EntityDamageByEntityEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            //getServer().broadcastMessage("hit");
            if (event.getDamager() instanceof Snowball) {
                //getServer().broadcastMessage("snowball hit");
                if(event.getDamager().isGlowing()) {
                    event.setDamage(2); // one heart of damage
                    //getServer().broadcastMessage("snowball hit " + event.getEntity().getName());
                }
            }
        }

    }

    private static void snowBallDelete(Snowball snowball) {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(Quacking.getPlugin(), new Runnable() {
            @Override
            public void run() {

                snowball.remove();
            }
        }, 20 * 3);
    }
}
