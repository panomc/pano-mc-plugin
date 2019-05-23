package com.panomc.plugins.pano.spigot.util

import com.panomc.plugins.pano.core.util.ServerConfiguration
import org.bukkit.plugin.java.JavaPlugin

class SpigotServerConfiguration(
    private val mJavaPlugin: JavaPlugin
) : ServerConfiguration(
    mJavaPlugin.server.name,
    mJavaPlugin.server.ip,
    mJavaPlugin.server.port,
    "Spigot",
    mJavaPlugin.server.version
) {
    override fun getPlayerCount() = mJavaPlugin.server.onlinePlayers.size

    override fun getMaxPlayerCount() = mJavaPlugin.server.maxPlayers
}