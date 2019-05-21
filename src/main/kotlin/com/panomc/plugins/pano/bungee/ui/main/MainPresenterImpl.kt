package com.panomc.plugins.pano.bungee.ui.main

import com.panomc.plugins.pano.bungee.BungeeMain
import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenter
import net.md_5.bungee.config.Configuration
import javax.inject.Inject

class MainPresenterImpl : MainPresenter {
    @Inject
    lateinit var configuration: Configuration

    @Inject
    lateinit var serverEventPresenter: ServerEventPresenter

    init {
        BungeeMain.getComponent().inject(this)
    }

    private fun isPlatformConfigured() = !configuration.getString("platform.token").isNullOrEmpty()

    override fun onServerStart() {
        serverEventPresenter.onStart(isPlatformConfigured())
    }
}