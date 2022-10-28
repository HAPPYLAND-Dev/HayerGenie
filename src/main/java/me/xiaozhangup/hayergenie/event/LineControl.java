package me.xiaozhangup.hayergenie.event;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.database.Island;
import me.xiaozhangup.hayergenie.GenieMaster;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class LineControl implements Listener {

    public static List<Island> online = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var p = e.getPlayer();
        var land = GenieMaster.landFromPlayer(p);
        if (land == null) return;
        if (!online.contains(land)) {
            Location geniePos = GenieMaster.getGeniePos(land);
            if (geniePos == null) return;
            online.add(land);
            GenieMaster.creatGenie(geniePos, p);
        }
    }


//写你🐎
//    @EventHandler
//    public void onPlayerTeleport(PlayerTeleportEvent e) {
//        var p = e.getPlayer();
//        var fromO = IridiumSkyblockAPI.getInstance().getIslandViaLocation(e.getFrom());
//        var toO = IridiumSkyblockAPI.getInstance().getIslandViaLocation(e.getTo());
//        if (toO.isPresent()) {
//            Island island = toO.get();
//            if (!online.contains(island)) {
//                online.add(island);
//                GenieMaster.creatGenie(GenieMaster.getGeniePos(island), p);
//            }
//        }
//        if (fromO.isPresent()) {
//
//        }
//    }

}
