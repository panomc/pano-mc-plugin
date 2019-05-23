package com.panomc.plugins.pano.bungee

import com.panomc.plugins.pano.bungee.command.PanoCommand
import com.panomc.plugins.pano.bungee.ui.main.MainPresenter
import com.panomc.plugins.pano.bungee.ui.main.MainPresenterImpl
import com.panomc.plugins.pano.bungee.util.BungeeServerConfiguration
import com.panomc.plugins.pano.bungee.util.Config
import com.panomc.plugins.pano.core.di.component.BungeeComponent
import com.panomc.plugins.pano.core.di.component.DaggerBungeeComponent
import com.panomc.plugins.pano.core.di.module.*
import com.panomc.plugins.pano.core.di.module.bungee.BungeePluginModule
import com.panomc.plugins.pano.core.util.ScheduleHelper
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.scheduler.ScheduledTask
import java.util.concurrent.TimeUnit

class BungeeMain : Plugin(), ScheduleHelper {
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

    private lateinit var mScheduledTask: ScheduledTask

    private val mPluginComponent by lazy {
        DaggerBungeeComponent
            .builder()
            .configModule(
                ConfigModule(
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
            .serverConfigurationModule(
                ServerConfigurationModule(
                    BungeeServerConfiguration(this)
                )
            )
            .scheduleHelperModule(ScheduleHelperModule(this))
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

    override fun onDisable() {
        proxy.scheduler.cancel(this)
    }

    private fun initializeCommands() {
        mCommands.forEach { command ->
            proxy.pluginManager.registerCommand(this, command)
        }
    }

    override fun startTask(callback: () -> Unit) {
        mScheduledTask = proxy.scheduler.schedule(this, {
            callback.invoke()

            startTask(callback)
        }, 1, TimeUnit.SECONDS)
    }

    override fun stopTask() {
        if (::mScheduledTask.isInitialized)
            mScheduledTask.cancel()
    }
}