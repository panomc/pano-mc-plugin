package com.panomc.plugins.pano.spigot.ui.main

import com.panomc.plugins.pano.core.ui.command.CommandPresenter
import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenter
import com.panomc.plugins.pano.core.util.CommandPresenterHelper
import com.panomc.plugins.pano.core.util.LanguageUtil
import com.panomc.plugins.pano.spigot.SpigotMain
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import javax.inject.Inject

class MainPresenterImpl : MainPresenter, CommandPresenterHelper {
    @Inject
    lateinit var plugin: JavaPlugin

    @Inject
    lateinit var serverEventPresenter: ServerEventPresenter

    @Inject
    lateinit var commandPresenter: CommandPresenter

    init {
        SpigotMain.getComponent().inject(this)
    }

    private fun isPlatformConfigured() = !plugin.config.getString("platform.token").isNullOrEmpty()

    override fun onServerStart() {
        serverEventPresenter.onStart(isPlatformConfigured())
    }

    override fun onPanoCommand(sender: CommandSender, args: Array<String>) {
        commandPresenter.onPanoCommand(sender, this, args)
    }

    override fun sendMessage(commandSender: Any, message: String) {
        (commandSender as CommandSender).sendMessage(LanguageUtil.translateColor(message))
    }
}