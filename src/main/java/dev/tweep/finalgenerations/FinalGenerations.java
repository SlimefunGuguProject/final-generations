package dev.tweep.finalgenerations;

import dev.tweep.finalgenerations.setup.FinalGenerationsSetup;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;

public class FinalGenerations extends JavaPlugin implements SlimefunAddon {

    private static FinalGenerations instance;
    private final String version = getClass().getPackage().getImplementationVersion();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        Config cfg = new Config(this);
        if (cfg.getBoolean("enable-experimental")) {
            FinalGenerationsSetup.RegisterExperimental();
        }
        FinalGenerationsSetup.RegisterItems();
        FinalGenerationsSetup.StartListeners();

        getLogger().info("FinalGenerations Slimefun Addon Plugin has started succesfully VER " + version);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/TweepCoding/final-generations/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static FinalGenerations getInstance() {
        return instance;
    }

}
