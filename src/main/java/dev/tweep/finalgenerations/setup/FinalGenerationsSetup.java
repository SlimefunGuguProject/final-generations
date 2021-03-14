package dev.tweep.finalgenerations.setup;

import dev.tweep.finalgenerations.FinalGenerations;
import dev.tweep.finalgenerations.items.misc.FlyingNimbus;
import dev.tweep.finalgenerations.items.weapons.Bazooka;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

/**
 * {@link FinalGenerationsSetup} registers all the items on the addon and fetches their properties from the
 * respective classes
 *
 * @see FinalGenerationsItems
 * @see FinalGenerationsRecipes
 * @see FinalGenerationsResearches
 */
public class FinalGenerationsSetup {

    private static final Logger logger = FinalGenerations.getInstance().getLogger();
    private static final FinalGenerations plugin = FinalGenerations.getInstance();

    public static void RegisterItems() {
        logger.info("Starting normal item registration");
        new SlimefunItem(FinalGenerationsItems.CATEGORY, FinalGenerationsItems.PIG_IRON_INGOT,
                RecipeType.SMELTERY, FinalGenerationsRecipes.EMPTY_RECIPE).register(plugin);

        new SlimefunItem(FinalGenerationsItems.CATEGORY, FinalGenerationsItems.PIG_SWORD,
                RecipeType.ENHANCED_CRAFTING_TABLE, FinalGenerationsRecipes.EMPTY_RECIPE).register(plugin);

        new Bazooka(FinalGenerationsItems.CATEGORY, FinalGenerationsItems.PIG_BAZOOKA, RecipeType.ENHANCED_CRAFTING_TABLE,
                FinalGenerationsRecipes.EMPTY_RECIPE, new ItemStack(Material.PORKCHOP), 4, EntityType.PIG).register(plugin);

        new FlyingNimbus(FinalGenerationsItems.CATEGORY, FinalGenerationsItems.FLYING_NIMBUS, RecipeType.ENHANCED_CRAFTING_TABLE,
                FinalGenerationsRecipes.EMPTY_RECIPE).register(plugin);
        logger.info("Finished normal item registration");
    }

    public static void RegisterExperimental() {
        logger.info("Starting experimental item registration: ");
        logger.info("Finished experimental item registration");
    }

    public static void StartListeners() {
        logger.info("Starting listeners: ");
        logger.info("Finished playing listeners");
    }
}
