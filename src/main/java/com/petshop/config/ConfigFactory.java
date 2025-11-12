package com.petshop.config;

import org.aeonbits.owner.ConfigCache;

/**
 * Factory class to create and cache configuration instances
 * Follows Singleton Pattern and Factory Pattern
 */
public class ConfigFactory {

    private ConfigFactory() {
        // Private constructor to prevent instantiation
    }

    /**
     * Get cached configuration instance
     * @return ConfigManager instance
     */
    public static ConfigManager getConfig() {
        return ConfigCache.getOrCreate(ConfigManager.class);
    }
}

