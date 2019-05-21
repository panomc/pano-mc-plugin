package com.panomc.plugins.pano.bungee

import com.panomc.plugins.pano.bungee.command.PanoCommand
import com.panomc.plugins.pano.bungee.ui.main.MainPresenter
import com.panomc.plugins.pano.bungee.ui.main.MainPresenterImpl
import com.panomc.plugins.pano.bungee.util.Config
import com.panomc.plugins.pano.core.di.component.BungeeComponent
import com.panomc.plugins.pano.core.di.component.DaggerBungeeComponent
import com.panomc.plugins.pano.core.di.module.LoggerModule
import com.panomc.plugins.pano.core.di.module.VertxModule
import com.panomc.plugins.pano.core.di.module.bungee.BungeeConfigurationModule
import com.panomc.plugins.pano.core.di.module.bungee.BungeePluginModule
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import net.md_5.bungee.api.plugin.Plugin

class BungeeMain : Plugin() {
    private val mVertxOptions = VertxOptions()
    private val mVertx = Vertx.vertx(mVertxOptions)

    private val mMainPresenter: MainPresenter by lazy {
        MainPresenterImpl()
    }

    private val mCommands by lazy {
        listOf(
            PanoCommand()
        )
    }

    private val mPluginComponent by lazy {
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
            .vertxModule(VertxModule(mVertx))
            .build()
    }

    companion object {
        private lateinit var mComponent: BungeeComponent

        internal fun getComponent() = mComponent
    }

    override fun onEnable() {
//        logger.info("Injecting modules.")

        logger.info("Initializing plugin.")

        mComponent = mPluginComponent

        initializeCommands()

        logger.info("Done.")

        mMainPresenter.onServerStart()
    }

    private fun initializeCommands() {
        mCommands.forEach { command ->
            proxy.pluginManager.registerCommand(this, command)
        }
    }
}