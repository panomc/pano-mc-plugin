package com.panomc.plugins.panoCore.spigot

import com.panomc.plugins.panoCore.di.component.DaggerSpigotComponent
import com.panomc.plugins.panoCore.di.module.LoggerModule
import com.panomc.plugins.panoCore.di.module.SpigotConfigurationModule
import com.panomc.plugins.panoCore.di.module.VertxModule
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class SpigotMain : JavaPlugin() {
    private val vertxOptions = VertxOptions()
    private val vertx = Vertx.vertx(vertxOptions)

    private val pluginComponent by lazy {
        DaggerSpigotComponent
            .builder()
            .spigotConfigurationModule(SpigotConfigurationModule(config))
            .loggerModule(LoggerModule(logger))
            .vertxModule(VertxModule(vertx))
            .build()
    }

    private fun createConfig() {
        try {
            if (!dataFolder.exists())
                dataFolder.mkdirs()

            val file = File(dataFolder, "config.yml")

            if (!file.exists()) {
                logger.info("Config.yml not found, creating!")
                saveDefaultConfig()
            } else
                logger.info("Config.yml found, loading!")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onEnable() {
        logger.info("Getting config.")

        createConfig()

        logger.info("Injecting modules.")

        pluginComponent.inject(this)

        logger.info("Initializing plugin.")

        logger.info("Done.")
    }
}