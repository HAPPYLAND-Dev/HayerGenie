package me.xiaozhangup.hayergenie.event;

import com.destroystokyo.paper.event.player.PlayerClientOptionsChangeEvent;
import ink.ptms.adyeshach.api.event.AdyeshachEntityInteractEvent;
import me.xiaozhangup.hayergenie.GenieMaster;
import me.xiaozhangup.hayergenie.Holder;
import me.xiaozhangup.hayergenie.utils.items.IBuilder;
import me.xiaozhangup.hayergenie.utils.items.Skull;
import me.xiaozhangup.hayergenie.utils.tools.IString;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GenieEvent implements Listener {

    public static ItemStack spawn = Skull.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTI4OWQ1YjE3ODYyNmVhMjNkMGIwYzNkMmRmNWMwODVlODM3NTA1NmJmNjg1YjVlZDViYjQ3N2ZlODQ3MmQ5NCJ9fX0=", "&x&c&e&d&e&f&e返回出生点 &7(主城)", "", "&7在游戏初期,你应当", "&7多在主城游览");
    public static ItemStack tree = Skull.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjc2YmQ5Y2M1NWI2YTI3NWIwYjZmYzc1NDk0NmE3NDgyM2ZhMmZlOTZiNzMyYzRkZDllMTk0NTE4MmZiYTUifX19", "&x&a&f&a&6&9&e前往树场", "", "&7你可以在这里获取", "&7大量矿物和树木");
    public static ItemStack quest = Skull.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjg2ZGYyNWE5ZDg1ZTc1NzU3ZjA0YjdkNzllYzc2MDc2YmY5NWRhZGE2OGYyY2YyYWY0OTRmOWY4OGZmMTg3MCJ9fX0=", "&x&f&7&e&3&a&4完成岛屿任务", "", "&7获取岛屿水晶和货币");
    public static ItemStack main = Skull.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE5M2IyNGQ3Y2IwZjg2M2I5ODdhNTRiMDI2YmQ5MDE4ZmFjMTU2NzFiM2ZiYmVjYzdkNmUyNTNjMjU3YiJ9fX0=", "&x&f&f&f&2&d&f打开主菜单", "", "&7打开服务器主菜单");
    public static ItemStack push = IBuilder.buildItem(Material.BARRIER, "&c从这里移除 Allay");

    @EventHandler
    public void playerClickBlock(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.CRAFTING_TABLE) {
                Player player = e.getPlayer();
                if (GenieMaster.isOnLand(player) && GenieMaster.getGenieID(GenieMaster.landFromPlayer(player)) == null) {
                    GenieMaster.creatGenie(e.getClickedBlock().getLocation(), player);
                    e.setCancelled(true);
                    player.sendMessage(IString.addColor("    "));
                    player.sendMessage(IString.addColor("    &f你好! 我是你的岛屿精灵 &bAllay&f!"));
                    player.sendMessage(IString.addColor("    &7我会在你岛屿的工作台上生成.当然,拆除工作台后我不会消失"));
                    player.sendMessage(IString.addColor("    &7右键我可以得到一些很基础的帮助内容."));
                    player.sendMessage(IString.addColor("    "));
                    player.sendMessage(IString.addColor("    &e当你岛屿没有Allay时,右键任意工作台将召唤Allay!"));
                    player.sendMessage(IString.addColor("    "));
                }
            }
        }
    }

    @EventHandler
    public void onNpc(AdyeshachEntityInteractEvent e) {
        Player player = e.getPlayer();
        if (e.isMainHand() && IString.addColor("&7[NPC] &bAllay").equals(e.getEntity().getCustomName())) {
            if (!GenieMaster.isOnLand(player)) {
                player.sendActionBar(MiniMessage.miniMessage().deserialize("<red>这不是你的岛屿悦灵</red>"));
                return;
            }
            Inventory inventory = Bukkit.createInventory(new Holder(), 27, MiniMessage.miniMessage().deserialize("你的岛屿悦灵"));

            ItemStack bg = IBuilder.getBorder(Material.GRAY_STAINED_GLASS_PANE);
            for (int i = 0; i < 27; i++) {
                inventory.setItem(i, bg);
            }

            inventory.setItem(11, spawn);
            inventory.setItem(12, tree);
            inventory.setItem(13, quest);
            inventory.setItem(15, main);
            inventory.setItem(26, push);

            player.openInventory(inventory);
        }
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player p && e.getInventory().getHolder() instanceof Holder) {
            e.setCancelled(true);
            p.closeInventory();
            switch (e.getRawSlot()) {
                case 11 -> Bukkit.dispatchCommand(p, "spawn");
                case 12 -> Bukkit.dispatchCommand(p, "warp treemaker");
                case 13 -> Bukkit.dispatchCommand(p, "istodo");
                case 15 -> Bukkit.dispatchCommand(p, "menu");
                case 26 -> GenieMaster.removeGenie(GenieMaster.landFromPlayer(p), false);
            }
        }
    }
}
