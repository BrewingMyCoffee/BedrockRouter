package com.bedrockrouter;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.geysermc.floodgate.api.FloodgateApi;

public class JoinListener implements Listener {

    private final BedrockRouter plugin;

    public JoinListener(BedrockRouter plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            ServerInfo target = plugin.getProxy().getServerInfo(plugin.getTargetServer());

            if (target == null) {
                plugin.getLogger().warning("Target server '" + plugin.getTargetServer() + "' not found in BungeeCord config!");
                return;
            }

            player.connect(target);
            plugin.getLogger().info("Routed Bedrock player " + player.getName() + " to " + plugin.getTargetServer());
        }
    }
}
