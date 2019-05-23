package com.panomc.plugins.pano.spigot.util

import com.panomc.plugins.pano.core.util.ConfigHelper
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

class Config(private val mJavaPlugin: JavaPlugin, private val mConfig: FileConfiguration) : ConfigHelper {
    override fun set(path: String, value: Any?) {
        mConfig.set(path, value)
    }

    override fun saveConfig() {
        mJavaPlugin.saveConfig()
    }

    override fun getString(path: String): String? = mConfig.getString(path)
}