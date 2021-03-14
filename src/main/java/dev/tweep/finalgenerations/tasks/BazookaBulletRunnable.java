package dev.tweep.finalgenerations.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.UUID;

/**
 * A {@link BazookaBulletRunnable} will be ran every time a bullet is shot by a {@link dev.tweep.finalgenerations.items.weapons.Bazooka}
 * A {@link BazookaBulletRunnable} should be ran as a repeating {@link BukkitRunnable}.
 * The task is self-cancellable, and it must not be cancelled outside of itself.
 */
public class BazookaBulletRunnable extends BukkitRunnable {

    private final Entity entity;
    private final Vector direction;
    private final float power;
    private final long maxAirTime = 3000;
    private final long startingTime;
    private final UUID shooterId;
    private static final HashSet<Material> nonCollisionBlocks = new HashSet<>();

    static {
        nonCollisionBlocks.add(Material.AIR);
        nonCollisionBlocks.add(Material.GRASS);
        nonCollisionBlocks.add(Material.TALL_GRASS);
        nonCollisionBlocks.add(Material.WATER);
        nonCollisionBlocks.add(Material.LAVA);
    }

    /**
     * @param entityType     The type of entity that will be created when the weapon is shot.
     * @param direction      The direction at which the shot was directed towards
     * @param playerLocation The location at which the shooter is located
     * @param power          The explosion power of the impact of the bullet
     * @param uuid           The UUID of the player who shot the weapon ( Won't explode when near )
     */
    public BazookaBulletRunnable(EntityType entityType, Vector direction, Location playerLocation, float power, UUID uuid) {
        entity = playerLocation.getWorld().spawnEntity(playerLocation.clone().add(direction.multiply(3)), entityType);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setAI(false);
            livingEntity.setCollidable(false);
        }
        entity.getLocation().setDirection(direction);
        this.direction = direction;
        this.power = power;
        shooterId = uuid;
        startingTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        for (Entity entityFound : entity.getWorld().getNearbyEntities(entity.getBoundingBox().expand(2))) {
            if (entityFound instanceof LivingEntity && !entityFound.equals(entity) && entityFound.getUniqueId() != shooterId) {
                explode();
            }
        }
        if (!nonCollisionBlocks.contains(entity.getLocation().getBlock().getType()) || System.currentTimeMillis() - startingTime > maxAirTime) {
            explode();
        } else {
            entity.teleport(entity.getLocation().add(direction.clone().multiply(0.25)));
            entity.getWorld().spawnParticle(Particle.CLOUD, entity.getLocation(), 1);
        }
    }

    private void explode() {
        entity.getWorld().createExplosion(entity.getLocation(), power, false, false, entity);
        entity.remove();
        cancel();
    }
}
