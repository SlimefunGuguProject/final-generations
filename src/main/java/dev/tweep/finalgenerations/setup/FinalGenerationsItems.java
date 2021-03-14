package dev.tweep.finalgenerations.setup;

import dev.tweep.finalgenerations.FinalGenerations;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

/**
 * {@link FinalGenerationsItems} contains the {@link SlimefunItemStack}s used for
 * registering the items added by this addon in {@link FinalGenerationsSetup}
 */
public class FinalGenerationsItems {

    public static final Category CATEGORY = new Category(new NamespacedKey(FinalGenerations.getInstance(), "category"),
            new CustomItem(Material.WARPED_WART_BLOCK, ChatColor.GREEN + "Final Generations"));

    public static final SlimefunItemStack PIG_IRON_INGOT = new SlimefunItemStack("PIG_IRON_INGOT", Material.IRON_INGOT,
            ChatColor.LIGHT_PURPLE + "Pig Iron Ingot"
    );

    public static final SlimefunItemStack PIG_SWORD = new SlimefunItemStack("PIG_SWORD", Material.IRON_SWORD,
            ChatColor.LIGHT_PURPLE + "Pig Iron Sword", "",
            ChatColor.DARK_PURPLE + "Drops porkchops randomly when hitting an enemy",
            ChatColor.DARK_PURPLE + "Maybe you can eat it in a pinch?"
    );

    public static final SlimefunItemStack PIG_BAZOOKA = new SlimefunItemStack("PIG_BAZOOKA", Material.IRON_HOE,
            ChatColor.LIGHT_PURPLE + "Pig Bazooka", "",
            ChatColor.DARK_PURPLE + "Uses porkchops as ammunition",
            ChatColor.LIGHT_PURPLE + "\"To whoever said pigs couldn't fly,",
            ChatColor.LIGHT_PURPLE + "you can now prove them wrong\""
    );

    public static final SlimefunItemStack FLYING_NIMBUS = new SlimefunItemStack("FLYING_NIMBUS", Material.YELLOW_DYE,
            ChatColor.YELLOW + "Flying Nimbus", "",
            ChatColor.GOLD + "I hope Dragon Ball Z doesn't",
            ChatColor.GOLD + "demand me for this",
            ChatColor.YELLOW + "Right Click to use");
}
