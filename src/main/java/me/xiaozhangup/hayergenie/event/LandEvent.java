package me.xiaozhangup.hayergenie.event;

import com.iridium.iridiumskyblock.api.IslandDeleteEvent;
import com.iridium.iridiumskyblock.api.IslandRegenEvent;
import me.xiaozhangup.hayergenie.GenieMaster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LandEvent implements Listener {

    @EventHandler
    public void landDelete(IslandDeleteEvent e) {
        GenieMaster.removeGenie(e.getIsland());
    }

    @EventHandler
    public void landRegen(IslandRegenEvent e) {
        GenieMaster.removeGenie(e.getIsland());
    }

}
