package com.quack.quacking.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Furnace;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public static ItemStack explodingWand;
    public static ItemStack flyStick;
    public static ItemStack testerBlock;
    public static ItemStack flightToggleStick;
    public static ItemStack snowBallCannon;
    public static ItemStack cyaStick;
    //add this to add later
    //public static ItemStack stick;

    //add ItemStack <name> to bottom to set the ItemStack <name> to be the name.

    public static void init() {
        //explodingWand();
        flyStick();
        //testerBlock();
        flightToggleStick();
        snowBallCannon();
        cyaStick();
    }

    private static void explodingWand() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§4dynamite stick");
        List<String> lore = new ArrayList<>();
        lore.add("§callahu akbar");
        //meta
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //set meta to itemstack
        item.setItemMeta(meta);
        //for some reason meta.addEnchant doesn't want to work, so this will do.
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 6969);
        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        //meta.addEnchant(Enchantment.LUCK, 1, false);
        explodingWand = item;
    }

    private static void flyStick() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dfly_stick");
        List<String> lore = new ArrayList<>();
        lore.add("§cright-click to fly");
        lore.add("§c§oCurse of Vanishing");
        lore.add("§c§oyou die, you lose the §dfly_stick");
        lore.add("§bQuackery");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.LUCK, 1);
        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        flyStick = item;
    }

    private static void cyaStick() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dcya");
        List<String> lore = new ArrayList<>();
        lore.add("§bKnockback 1234");
        lore.add("§bQuackery");
        meta.setLore(lore);
        //meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 6969);
        cyaStick = item;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("cyastick"), item);
        sr.shape("BXB",
                "XBX",
                "BXB");
        //b diamond, x stick
        sr.setIngredient('B', Material.DIAMOND);
        sr.setIngredient('X', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

    }

    //special furnace, maybe for future
    private static void testerBlock() {
        ItemStack item = new ItemStack(Material.FURNACE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dSpecial Furnace");
        List<String> lore = new ArrayList<>();
        lore.add("§cSpecial furnace for special items");
        lore.add("§bQuackery");
        meta.setLore(lore);
        item.setItemMeta(meta);
        testerBlock = item;
    }

    private static void flightToggleStick() {
        ItemStack item = new ItemStack(Material.GOLDEN_HOE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dtoggle_flight_hoe");
        List<String> lore = new ArrayList<>();
        lore.add("§cRight-click to toggle flight.");
        lore.add("§c§oyou die, you lose the §dtoggle_flight_hoe");
        lore.add("§c§oCurse of Vanishing");
        lore.add("§bQuackery");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.LUCK, 1);
        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);

        flightToggleStick = item;
        //furnace smelting the flightToggleStick
        FurnaceRecipe smelt = new FurnaceRecipe(NamespacedKey.minecraft("flighttogglestick_smelt"), item,
                Material.TOTEM_OF_UNDYING, 2.0f, 1020 * 20); //change///////////////// 17 mins rn
        Bukkit.getServer().addRecipe(smelt);
    }

    private static void snowBallCannon() {
        ItemStack item = new ItemStack(Material.STICK, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dsnowBall_cannon");
        List<String> lore = new ArrayList<>();
        lore.add("§cright-click to shoot snowballs");
        lore.add("§c§oCurse of Vanishing");
        lore.add("§c§oyou die, you lose the §dsnowBall_cannon");
        lore.add("§bQuackery");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.LUCK, 1);
        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        snowBallCannon = item;
    }

}
