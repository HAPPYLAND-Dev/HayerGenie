package me.xiaozhangup.hayergenie;

import me.xiaozhangup.hayergenie.event.GenieEvent;
import me.xiaozhangup.hayergenie.event.LandEvent;
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

        listenerManager.addListeners(
                new LandEvent(), new GenieEvent()
        );
        listenerManager.register();

        Command.register("geniedebugcreat", (commandSender, command, s, inside) -> {
            Player p = (Player) commandSender;
            GenieMaster.creatGenie(p.getTargetBlock(6).getLocation(), p);
            return false;
        });

        ConfigManager.createFile(LANDGENIE);



    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }
    }

}
