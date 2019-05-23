package com.panomc.plugins.pano.core.util

import java.net.InetAddress

abstract class ServerConfiguration(
    open val serverName: String,
    open val hostAddress: String = InetAddress.getLocalHost().hostAddress,
    open val port: Int,
    open val serverType: String,
    open val serverVersion: String
) {
    abstract fun getPlayerCount(): Int

    abstract fun getMaxPlayerCount(): Int
}