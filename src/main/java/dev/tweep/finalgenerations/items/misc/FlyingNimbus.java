package dev.tweep.finalgenerations.items.misc;

import dev.tweep.finalgenerations.FinalGenerations;
import dev.tweep.finalgenerations.tasks.FlyingNimbusRunnable;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.UUID;

/**
 * A {@link FlyingNimbus} will create a flying nimbus cloud on the sky for players to travel in.
 * This will start a {@link FlyingNimbusRunnable}, which is used to make the nimbus follow the player
 */
public class FlyingNimbus extends SlimefunItem {

    private static final HashSet<UUID> usingNimbus = new HashSet<>();

    public FlyingNimbus(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        ItemUseHandler blockUseHandler = this::onRightClick;
        addItemHandler(blockUseHandler);
    }

    private void onRightClick(PlayerRightClickEvent event) {
        if (usingNimbus.contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(ChatColor.RED + "You must get out of your current nimbus before riding another one");
        } else {
            event.getPlayer().setVelocity(event.getPlayer().getVelocity().add(new Vector(0, 2.5, 0)));
            usingNimbus.add(event.getPlayer().getUniqueId());
            new FlyingNimbusRunnable(event.getPlayer()).runTaskTimer(FinalGenerations.getInstance(), 40, 3);
        }
    }

    public static void removePlayer(UUID id) {
        usingNimbus.remove(id);
    }
}
