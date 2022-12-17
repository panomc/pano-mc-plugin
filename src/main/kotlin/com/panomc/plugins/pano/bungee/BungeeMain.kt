package com.panomc.plugins.pano.bungee

import com.panomc.plugins.pano.core.Pano
import com.panomc.plugins.pano.core.command.Command
import com.panomc.plugins.pano.core.helper.PanoPluginMain
import com.panomc.plugins.pano.core.helper.ServerData
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.scheduler.ScheduledTask
import java.util.concurrent.TimeUnit

class BungeeMain : Plugin(), PanoPluginMain {
    private lateinit var pano: Pano
    private val scheduledTasks = mutableMapOf<() -> Unit, ScheduledTask>()
    private val serverData by lazy { BungeeServerData(this) }

    override fun onEnable() {
        pano = Pano.init(this)

        pano.onServerStart()
    }

    override fun onDisable() {
        if (::pano.isInitialized) {
            pano.disable()
        }
    }

    override fun registerCommands(commands: List<Command>) {
        commands
            .map { BungeeCommand(it) }
            .forEach { command ->
                logger.info("command registered")
                proxy.pluginManager.registerCommand(this, command)
            }
    }

    override fun unregisterCommands(commands: List<Command>) {
        proxy.pluginManager.unregisterCommands(this)
    }

    override fun registerSchedule(task: () -> Unit) {
        if (scheduledTasks.containsKey(task)) {
            stopSchedule(task)
        }

        scheduledTasks[task] = proxy.scheduler.schedule(this, {
            task.invoke()

            registerSchedule(task)
        }, 1, TimeUnit.SECONDS)
    }

    override fun stopSchedule(task: () -> Unit) {
        scheduledTasks[task]?.cancel()
        scheduledTasks.remove(task)
    }

    override fun unregisterSchedules(tasks: List<() -> Unit>) {
        tasks.forEach { task ->
            stopSchedule(task)
        }
    }

    override fun getServerData(): ServerData = serverData
    override fun getPluginClassLoader(): ClassLoader = javaClass.classLoader
}