package com.panomc.plugins.panoCore.bungee

import net.md_5.bungee.api.plugin.Plugin

class BungeeMain : Plugin() {

//    private val pluginComponent by lazy {
//        DaggerBungeeComponent
//                .builder()
//                .configurationModule(ConfigurationModule(Config(dataFolder, logger, getResourceAsStream("config.yml"))))
//                .loggerModule(LoggerModule(logger))
//                .pluginModule(PluginModule(this))
//                .build()
//    }

    override fun onEnable() {
        logger.info("Injecting modules.")

//        pluginComponent.inject(this)

        logger.info("Initializing plugin.")

//        proxy.pluginManager.registerListener(this, connectionListener)

        logger.info("Done.")
    }
}