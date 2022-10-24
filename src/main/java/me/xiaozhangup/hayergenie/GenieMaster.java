package me.xiaozhangup.hayergenie;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.database.Island;
import com.iridium.iridiumskyblock.database.User;
import ink.ptms.adyeshach.Adyeshach;
import ink.ptms.adyeshach.api.AdyeshachAPI;
import ink.ptms.adyeshach.common.entity.EntityTypes;
import ink.ptms.adyeshach.common.entity.ai.expand.ControllerLookAtPlayerAlways;
import ink.ptms.adyeshach.internal.trait.impl.TraitTitleKt;
import me.xiaozhangup.hayergenie.utils.manager.ConfigManager;
import me.xiaozhangup.hayergenie.utils.tools.IString;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

import static me.xiaozhangup.hayergenie.HayerGenie.LANDGENIE;

public class GenieMaster {

    public static void creatGenie(Location location, Player p) {
        location.add(0.5D, 1.2D, 0.5D);

        var npc = AdyeshachAPI.INSTANCE.getEntityManagerPublic().create(EntityTypes.ALLAY, location);
        npc.registerController(new ControllerLookAtPlayerAlways(npc));
        npc.setCustomName(IString.addColor("&7[NPC] &bAllay"));
        npc.setCustomNameVisible(true);
        TraitTitleKt.setTraitTitle(npc, List.of(IString.addColor("&e单击交互"), ""));

        ConfigManager.writeConfig(LANDGENIE, String.valueOf(landFromPlayer(p).getId()), npc.getUniqueId());
    }

    public static String getGenieID(Island island) {
        return ConfigManager.getConfig(LANDGENIE).getString(String.valueOf(island.getId()));
    }

    public static void removeGenie(Island island) {
        var id = getGenieID(island);
        if (id == null) return;
        AdyeshachAPI.INSTANCE.getEntityManagerPublic().getEntityByUniqueId(id).delete();
        ConfigManager.writeConfig(LANDGENIE, String.valueOf(island.getId()), null);
    }

    public static boolean isOnLand(Player p) {
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(p);
        return IridiumSkyblock.getInstance().getIslandManager().getPlayersOnIsland(landFromPlayer(p)).contains(user);
    }

    public static Island landFromPlayer(Player p) {
        User user = IridiumSkyblock.getInstance().getUserManager().getUser(p);
        var is = user.getIsland();
        return is.orElse(null);
    }

}
