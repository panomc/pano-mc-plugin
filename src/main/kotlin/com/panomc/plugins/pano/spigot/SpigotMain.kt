package com.panomc.plugins.pano.spigot

import com.panomc.plugins.pano.core.di.component.DaggerSpigotComponent
import com.panomc.plugins.pano.core.di.component.SpigotComponent
import com.panomc.plugins.pano.core.di.module.*
import com.panomc.plugins.pano.core.di.module.spigot.SpigotPluginModule
import com.panomc.plugins.pano.core.util.ScheduleHelper
import com.panomc.plugins.pano.spigot.ui.main.MainPresenter
import com.panomc.plugins.pano.spigot.ui.main.MainPresenterImpl
import com.panomc.plugins.pano.spigot.util.Config
import com.panomc.plugins.pano.spigot.util.SpigotServerConfiguration
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class SpigotMain : JavaPlugin(), ScheduleHelper {
    private val mVertxOptions = VertxOptions()
    private val mVertx = Vertx.vertx(mVertxOptions)

    private var mTaskID = 0

    private val mMainPresenter: MainPresenter by lazy {
        MainPresenterImpl()
    }

    private val mPluginComponent by lazy {
        DaggerSpigotComponent
            .builder()
            .configModule(ConfigModule(Config(this, config)))
            .loggerModule(LoggerModule(logger))
            .vertxModule(VertxModule(mVertx))
            .spigotPluginModule(SpigotPluginModule(this))
            .serverConfigurationModule(
                ServerConfigurationModule(
                    SpigotServerConfiguration(this)
                )
            )
            .scheduleHelperModule(ScheduleHelperModule(this))
            .build()
    }

    companion object {
        private lateinit var mComponent: SpigotComponent

        internal fun getComponent() = mComponent
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

        mComponent = mPluginComponent

        logger.info("Initializing plugin.")

        server.scheduler.scheduleSyncDelayedTask(this) {
            mMainPresenter.onServerStart()
        }

        logger.info("Done.")
    }

    override fun onDisable() {
        server.scheduler.cancelTasks(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (label.equals("pano", true))
            mMainPresenter.onPanoCommand(sender, args)

        return super.onCommand(sender, command, label, args)
    }

    override fun startTask(callback: () -> Unit) {
        mTaskID = server.scheduler.scheduleSyncRepeatingTask(this, callback, 1, 20)
    }

    override fun stopTask() {
        server.scheduler.cancelTask(mTaskID)
    }
}