package com.github.shin19;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.shanerx.tradeshop.framework.events.PlayerSuccessfulTradeEvent;

public class TradeShopAddon extends JavaPlugin implements Listener {
    private CoreProtectAPI coreProtect = null;

    @Override
    public void onEnable() {
        coreProtect = getCoreProtect();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerTrade(PlayerSuccessfulTradeEvent event) {
        if (coreProtect != null) {
            coreProtect.logContainerTransaction(event.getPlayer().getDisplayName(), event.getShop().getInventoryLocationAsSL().getLocation());
        }
    }

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (!(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 6) {
            return null;
        }

        return CoreProtect;
    }
}
