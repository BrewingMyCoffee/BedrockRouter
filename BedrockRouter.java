package com.bedrockrouter;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BedrockRouter extends Plugin {

    private String targetServer;

    @Override
    public void onEnable() {
        targetServer = loadTargetServer();
        getProxy().getPluginManager().registerListener(this, new JoinListener(this));
        getLogger().info("BedrockRouter enabled. Routing Bedrock players to: " + targetServer);
    }

    private String loadTargetServer() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                if (in != null) {
                    Files.copy(in, configFile.toPath());
                }
            } catch (IOException e) {
                getLogger().warning("Could not save default config: " + e.getMessage());
            }
        }

        try {
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            return config.getString("target-server", "t303");
        } catch (IOException e) {
            getLogger().warning("Could not load config, using default: " + e.getMessage());
            return "t303";
        }
    }

    public String getTargetServer() {
        return targetServer;
    }
}
