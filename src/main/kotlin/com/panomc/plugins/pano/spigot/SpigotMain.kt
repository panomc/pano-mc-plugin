package com.panomc.plugins.pano.spigot

import com.panomc.plugins.pano.core.di.component.DaggerSpigotComponent
import com.panomc.plugins.pano.core.di.component.SpigotComponent
import com.panomc.plugins.pano.core.di.module.LoggerModule
import com.panomc.plugins.pano.core.di.module.SpigotConfigurationModule
import com.panomc.plugins.pano.core.di.module.SpigotPluginModule
import com.panomc.plugins.pano.core.di.module.VertxModule
import com.panomc.plugins.pano.spigot.ui.main.MainPresenter
import com.panomc.plugins.pano.spigot.ui.main.MainPresenterImpl
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class SpigotMain : JavaPlugin() {
    private val mVertxOptions = VertxOptions()
    private val mVertx = Vertx.vertx(mVertxOptions)

    private val mMainPresenter: MainPresenter by lazy {
        MainPresenterImpl()
    }

    private val mPluginComponent by lazy {
        DaggerSpigotComponent
            .builder()
            .spigotConfigurationModule(SpigotConfigurationModule(config))
            .loggerModule(LoggerModule(logger))
            .vertxModule(VertxModule(mVertx))
            .spigotPluginModule(SpigotPluginModule(this))
            .build()
    }

    companion object {
        private lateinit var component: SpigotComponent

        internal fun getComponent() = component
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

        component = mPluginComponent

        logger.info("Initializing plugin.")

        server.scheduler.scheduleSyncDelayedTask(this) {
            mMainPresenter.onServerStart()
        }

        logger.info("Done.")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        println(label)

        return super.onCommand(sender, command, label, args)
    }
}