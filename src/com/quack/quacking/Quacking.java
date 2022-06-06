package com.quack.quacking;

import com.quack.quacking.commands.QuackingCommands;
import com.quack.quacking.commands.SnowBallCannon;
import com.quack.quacking.events.PersistDataStorage;
import com.quack.quacking.events.QuackingEvents;
import com.quack.quacking.events.StartFuelChecker;
import com.quack.quacking.events.TNTlogger;
import com.quack.quacking.files.DataManager;
import com.quack.quacking.items.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class Quacking extends JavaPlugin {

    private static Quacking plugin;

    public DataManager data;

    @Override
    public void onEnable() {
        plugin = this;
        this.data = new DataManager(this);
        QuackingEvents.dataIncoming = this.data;
        ItemManager.init();
        QuackingCommands stickCommands = new QuackingCommands();
        //events. Other events["dumpClasses"]
        getServer().getPluginManager().registerEvents(new QuackingEvents(), this);
        getServer().getPluginManager().registerEvents(new PersistDataStorage(), this);
        getServer().getPluginManager().registerEvents(new SnowBallCannon(), this);
        getServer().getPluginManager().registerEvents(new TNTlogger(), this);
        getCommand("dynamitestick").setExecutor(stickCommands);
        getCommand("flystick").setExecutor(stickCommands);
        getCommand("snowball").setExecutor(stickCommands);
        getCommand("flight_stick_info").setExecutor(stickCommands);
        getCommand("cyastick").setExecutor(stickCommands);
        getCommand("refreshfuel").setExecutor(stickCommands);


        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                // Do something
                new StartFuelChecker().fuelChecker(new DataManager(Quacking.getPlugin()));
            }
        }, 20 * 2);



        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Quacking] Plugin loaded");
    }

    @Override
    public void onDisable() {

    }
    //getter for plugin
    public static Quacking getPlugin() {
        return plugin;
    }





}


