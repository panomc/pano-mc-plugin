package com.panomc.plugins.pano.bungee.util

import com.panomc.plugins.pano.core.util.ServerConfiguration
import net.md_5.bungee.api.config.ListenerInfo
import net.md_5.bungee.api.plugin.Plugin

class BungeeServerConfiguration(
    private val mPlugin: Plugin
) : ServerConfiguration(
    mPlugin.proxy.name,
    (mPlugin.proxy.config.listeners.toList()[0] as ListenerInfo).host.hostString,
    (mPlugin.proxy.config.listeners.toList()[0] as ListenerInfo).host.port,
    "Bungeecord",
    mPlugin.proxy.version
) {
    override fun getPlayerCount() = mPlugin.proxy.players.size

    override fun getMaxPlayerCount() = mPlugin.proxy.config.playerLimit
}