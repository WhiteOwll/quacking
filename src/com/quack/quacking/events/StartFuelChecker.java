package com.quack.quacking.events;

import com.quack.quacking.Quacking;
import com.quack.quacking.files.DataManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import static com.quack.quacking.events.QuackingEvents.checkAllPlayerFuel;
import static org.bukkit.Bukkit.getServer;




public class StartFuelChecker {
    //check for fuel

    public static BukkitScheduler fuelCheckerScheduler;
    public static int fuelCheckerTask;
    

    public void fuelChecker(DataManager data) {

        if(data == null) return;
        int fuelTime = 100; //default
        if(data.getConfig().contains("fuelTime"))
            fuelTime = data.getConfig().getInt("fuelTime");

        BukkitScheduler scheduler = getServer().getScheduler();
        int task = scheduler.scheduleSyncRepeatingTask(Quacking.getPlugin(), new Runnable() {
            @Override
            public void run() {
                checkAllPlayerFuel();
            }
        }, fuelTime * 20L, fuelTime * 20L);
        fuelCheckerScheduler = scheduler;
        fuelCheckerTask = task;
    }

}
