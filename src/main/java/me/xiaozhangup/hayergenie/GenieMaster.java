package me.xiaozhangup.hayergenie;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import ink.ptms.adyeshach.api.AdyeshachAPI;
import ink.ptms.adyeshach.common.entity.EntityInstance;
import ink.ptms.adyeshach.common.entity.EntityTypes;
import ink.ptms.adyeshach.common.entity.ai.expand.ControllerLookAtPlayerAlways;
import ink.ptms.adyeshach.internal.trait.impl.TraitTitleKt;
import me.xiaozhangup.hayergenie.event.GenieEvent;
import me.xiaozhangup.hayergenie.event.LineControl;
import me.xiaozhangup.hayergenie.utils.manager.ConfigManager;
import me.xiaozhangup.hayergenie.utils.tools.IString;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

import static me.xiaozhangup.hayergenie.HayerGenie.LANDGENIE;

public class GenieMaster {

    public static void creatGenie(Location location, Player p) {
        Island island = landFromPlayer(p);
        if (island == null) return;
        ConfigManager.writeConfig(LANDGENIE, island.getId() + ".pos", location);

        location.add(0.5D, 1.2D, 0.5D);

        var npc = AdyeshachAPI.INSTANCE.getEntityManagerPublic().create(EntityTypes.ALLAY, location);
        npc.registerController(new ControllerLookAtPlayerAlways(npc));
        npc.setCustomName(IString.addColor("&7[NPC] &bAllay"));
        npc.setCustomNameVisible(true);
        TraitTitleKt.setTraitTitle(npc, List.of(IString.addColor("&e单击交互"), ""));

        ConfigManager.writeConfig(LANDGENIE, island.getId() + ".uuid", npc.getUniqueId());
        LineControl.online.add(island);
    }

    public static String getGenieID(Island island) {
        if (island == null) return null;
        return ConfigManager.getConfig(LANDGENIE).getString(island.getId() + ".uuid");
    }

    public static Location getGeniePos(Island island) {
        return ConfigManager.getConfig(LANDGENIE).getLocation(island.getId() + ".pos");
    }

    public static void removeGenie(Island island, boolean savePos) {
        if (island == null) return;
        var id = getGenieID(island);
        if (id == null) return;
        EntityInstance entityByUniqueId = AdyeshachAPI.INSTANCE.getEntityManagerPublic().getEntityByUniqueId(id);
        if (entityByUniqueId != null)
            entityByUniqueId.delete();
        ConfigManager.writeConfig(LANDGENIE, island.getId() + ".uuid", null);
        if (!savePos) {
            ConfigManager.writeConfig(LANDGENIE, island.getId() + ".pos", null);
        }
    }

    public static boolean isOnLand(Player p) {
        if (landFromPlayer(p) == null) return false;
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(p);
        return IridiumSkyblock.getInstance().getIslandManager().getPlayersOnIsland(landFromPlayer(p)).contains(user);
    }

    public static @Nullable Island landFromPlayer(Player p) {
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(p);
        var is = user.getIsland();
        return is.orElse(null);
    }

}
