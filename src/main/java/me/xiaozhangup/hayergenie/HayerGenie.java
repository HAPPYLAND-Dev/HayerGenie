package me.xiaozhangup.hayergenie;

import me.xiaozhangup.hayergenie.utils.manager.ListenerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HayerGenie extends JavaPlugin {

    public static Plugin plugin;
    public static ListenerManager listenerManager = new ListenerManager();
    private static Economy econ = null;

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onEnable() {
        plugin = this;
        setupEconomy();

//        listenerManager.addListeners(
//                /*Your event*/
//        );
//        listenerManager.register();

//        Command.register("example", (commandSender, command, s, inside) -> {
//            /*your way*/
//            return false;
//        });



    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }
    }

}
