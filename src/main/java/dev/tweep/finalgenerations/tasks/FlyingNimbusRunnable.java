package dev.tweep.finalgenerations.tasks;

import dev.tweep.finalgenerations.items.misc.FlyingNimbus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

/**
 * A {@link FlyingNimbusRunnable} will be ran every time a Nimbus is created
 * This should be ran as a repeating {@link BukkitRunnable}.
 * This task is self-cancellable, and it must not be cancelled outside of itself.
 */
public class FlyingNimbusRunnable extends BukkitRunnable {

    private final Player rider;
    private final HashSet<Location> previousWoolLocation = new HashSet<>();

    /**
     * @param rider The player who is riding the flying nimbus
     */
    public FlyingNimbusRunnable(Player rider) {
        this.rider = rider;
    }

    @Override
    public void run() {
        if (!rider.isOnline()) {
            destroyNimbus();
            FlyingNimbus.removePlayer(rider.getUniqueId());
            cancel();
        }
        if (rider.isSneaking()) {
            Location nextLocation = rider.getLocation().clone().subtract(0, 3, 0);
            destroyNimbus();
            if (nextLocation.getBlock().getType() == Material.AIR) {
                rider.teleport(nextLocation);
            } else {
                FlyingNimbus.removePlayer(rider.getUniqueId());
                cancel();
            }
        } else {
            moveNimbus(rider.getLocation());
        }
    }

    private void moveNimbus(Location nimbusLocation) {
        destroyNimbus();
        if (isAreaClear(nimbusLocation)) {
            createNimbus(nimbusLocation);
        } else {
            FlyingNimbus.removePlayer(rider.getUniqueId());
            cancel();
        }
    }

    private void createNimbus(Location nimbusLocation) {
        for (int y = 1; y < 3; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    Location newWool = nimbusLocation.clone().subtract(x, y, z);
                    newWool.getBlock().setType(Material.YELLOW_WOOL);
                    previousWoolLocation.add(newWool);
                }
            }
        }
    }

    private void destroyNimbus() {
        for (Location wool : previousWoolLocation) {
            wool.getBlock().setType(Material.AIR);
        }
        previousWoolLocation.clear();
    }

    private boolean isAreaClear(Location nimbusLocation) {
        for (int y = 1; y < 3; y++) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    Location blockToCheck = nimbusLocation.clone().subtract(x, y, z);
                    if (blockToCheck.getBlock().getType() != Material.AIR) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
