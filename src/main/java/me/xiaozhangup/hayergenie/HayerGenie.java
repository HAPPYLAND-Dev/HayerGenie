package me.xiaozhangup.hayergenie;

import ink.ptms.adyeshach.api.AdyeshachAPI;
import ink.ptms.adyeshach.common.entity.EntityInstance;
import me.xiaozhangup.hayergenie.event.GenieEvent;
import me.xiaozhangup.hayergenie.event.LandEvent;
import me.xiaozhangup.hayergenie.event.LineControl;
import me.xiaozhangup.hayergenie.utils.command.Command;
import me.xiaozhangup.hayergenie.utils.manager.ConfigManager;
import me.xiaozhangup.hayergenie.utils.manager.ListenerManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HayerGenie extends JavaPlugin {

    public static final String LANDGENIE = "landgenie";
    public static Plugin plugin;
    public static ListenerManager listenerManager = new ListenerManager();
    private static Economy econ = null;

    public static Economy getEconomy() {
        return econ;
    }

    public static MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        plugin = this;
        setupEconomy();

        saveDefaultConfig();
        reloadConfig();
        ConfigManager.createFile(LANDGENIE);

        listenerManager.addListeners(
                new LandEvent(), new GenieEvent(), new LineControl()
        );
        listenerManager.register();

        Command.register("geniedebugcreat", (commandSender, command, s, inside) -> {
            Player p = (Player) commandSender;
            if (!p.isOp()) return false;
            GenieMaster.creatGenie(p.getTargetBlock(6).getLocation(), p);
            return false;
        });

        //remove error
        ConfigManager.getConfig(LANDGENIE).getKeys(false).forEach((s -> {
            var uuid = ConfigManager.getConfig(LANDGENIE).getString(s+".uuid");
            if (uuid != null) {
                getLogger().info(uuid + " Removing...");
                EntityInstance entityByUniqueId = AdyeshachAPI.INSTANCE.getEntityManagerPublic().getEntityByUniqueId(uuid);
                if (entityByUniqueId != null)
                    entityByUniqueId.delete();
                ConfigManager.writeConfig(LANDGENIE, s + ".uuid", null);
            }
        }));

    }

    @Override
    public void onDisable() {
        LineControl.online.forEach((island -> {
            GenieMaster.removeGenie(island, true);
            getLogger().info(island + " Removed...");
        }));
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }
    }

}
