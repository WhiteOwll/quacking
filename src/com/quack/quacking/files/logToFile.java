package com.quack.quacking.files;

import com.quack.quacking.Quacking;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class logToFile {
    public static void logToFile(String message) {

        Plugin plugin = Quacking.getPlugin();

        try
        {
            File dataFolder = plugin.getDataFolder();
            if(!dataFolder.exists())
            {
                dataFolder.mkdir();
            }

            File saveTo = new File(plugin.getDataFolder(), "TNTLogger.log");
            if (!saveTo.exists())
            {
                saveTo.createNewFile();
            }


            FileWriter fw = new FileWriter(saveTo, true);

            PrintWriter pw = new PrintWriter(fw);

            pw.println(message);

            pw.flush();

            pw.close();

        } catch (IOException e)
        {

            e.printStackTrace();

        }

    }

}
