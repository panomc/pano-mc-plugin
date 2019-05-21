package com.panomc.plugins.pano.spigot.ui.main

import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenter
import com.panomc.plugins.pano.spigot.SpigotMain
import org.bukkit.plugin.java.JavaPlugin
import javax.inject.Inject

class MainPresenterImpl : MainPresenter {
    @Inject
    lateinit var plugin: JavaPlugin

    @Inject
    lateinit var serverEventPresenter: ServerEventPresenter

    init {
        SpigotMain.getComponent().inject(this)
    }

    private fun isPlatformConfigured() = !plugin.config.getString("platform.token").isNullOrEmpty()

    override fun onServerStart() {
        serverEventPresenter.onStart(isPlatformConfigured())
    }
}