package com.quack.quacking.events;

import com.quack.quacking.files.logToFile;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TNTlogger implements Listener {

    @EventHandler
    public void TNTlogger(BlockPlaceEvent event) {

        Block block = event.getBlock();
        Material mat = block.getType();
        Player player = event.getPlayer();

        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        if(mat.equals(Material.TNT)) {
            //player.sendMessage("you placed tnt at " + x + " " + y + " " + z);
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            logToFile.logToFile(player.getName() + " placed TNT @ X:" + x + " Y:" + y + " Z:" + z + "  (" + timeStamp + ")");
        }

    }

}
