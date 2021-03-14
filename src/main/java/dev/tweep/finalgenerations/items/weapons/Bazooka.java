package dev.tweep.finalgenerations.items.weapons;

import dev.tweep.finalgenerations.FinalGenerations;
import dev.tweep.finalgenerations.tasks.BazookaBulletRunnable;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

/**
 * The {@link Bazooka} is the main skeleton for most bazookas on this plugin.
 * A {@link Bazooka} will shoot on a {@link PlayerRightClickEvent} the entity that
 * it recieved via it's constructor.
 */
public class Bazooka extends SlimefunItem {

    private final ItemStack ammo;
    private final float power;
    private final EntityType entityType;
    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    /**
     * @param ammo       ItemStack that will be used as ammunition for the bazooka
     * @param power      Sets the explosion power of the weapon
     * @param entityType Sets the entity type that will be shot by the bazooka
     */
    public Bazooka(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, ItemStack ammo, float power, EntityType entityType) {
        super(category, item, type, recipe);
        this.ammo = ammo;
        this.power = power;
        this.entityType = entityType;
    }

    @Override
    public void preRegister() {
        ItemUseHandler blockUseHandler = this::onRightClick;
        addItemHandler(blockUseHandler);
    }

    private void onRightClick(PlayerRightClickEvent event) {
        Player player = event.getPlayer();
        if (!cooldown.containsKey(player.getUniqueId())) {
            lookForAmmo(player);
        } else {
            long timeLeft = cooldown.get(player.getUniqueId()) - System.currentTimeMillis();
            if (timeLeft <= 0) {
                cooldown.remove(player.getUniqueId());
                lookForAmmo(player);
            } else {
                player.sendMessage(ChatColor.RED + "You must wait % seconds before doing this action".replace("%",
                        ((Long) (timeLeft / 1000)).toString()));
            }
        }
    }

    private void lookForAmmo(Player shooter) {
        for (ItemStack item : shooter.getInventory().getContents()) {
            if (item != null && (item.getItemMeta() == null || item.getItemMeta().equals(ammo.getItemMeta())) && item.getType() == ammo.getType() && item.getAmount() >= ammo.getAmount()) {
                item.setAmount(item.getAmount() - ammo.getAmount());
                cooldown.put(shooter.getUniqueId(), System.currentTimeMillis() + (3000));
                shoot(shooter);
                break;
            }
        }
    }

    private void shoot(Player shooter) {
        Vector shotDirection = shooter.getEyeLocation().getDirection().normalize();
        shooter.setVelocity(shooter.getVelocity().add(shotDirection.clone().multiply(-0.5)));
        shooter.getWorld().playSound(shooter.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 0.3f, 1);
        new BazookaBulletRunnable(entityType, shotDirection, shooter.getEyeLocation(),
                power, shooter.getUniqueId()).runTaskTimer(FinalGenerations.getInstance(), 0, 0);
    }
}
