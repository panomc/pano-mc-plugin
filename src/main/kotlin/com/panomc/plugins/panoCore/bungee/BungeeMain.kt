package com.panomc.plugins.panoCore.bungee

import com.panomc.plugins.panoCore.bungee.util.Config
import com.panomc.plugins.panoCore.di.component.DaggerBungeeComponent
import com.panomc.plugins.panoCore.di.module.BungeeConfigurationModule
import com.panomc.plugins.panoCore.di.module.BungeePluginModule
import com.panomc.plugins.panoCore.di.module.LoggerModule
import com.panomc.plugins.panoCore.di.module.VertxModule
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import net.md_5.bungee.api.plugin.Plugin

class BungeeMain : Plugin() {
    private val vertxOptions = VertxOptions()
    private val vertx = Vertx.vertx(vertxOptions)

    private val pluginComponent by lazy {
        DaggerBungeeComponent
            .builder()
            .bungeeConfigurationModule(
                BungeeConfigurationModule(
                    Config(
                        dataFolder,
                        logger,
                        getResourceAsStream("config.yml")
                    )
                )
            )
            .loggerModule(LoggerModule(logger))
            .bungeePluginModule(BungeePluginModule(this))
            .vertxModule(VertxModule(vertx))
            .build()
    }

    override fun onEnable() {
        logger.info("Injecting modules.")

        pluginComponent.inject(this)

        logger.info("Initializing plugin.")

        logger.info("Done.")
    }
}