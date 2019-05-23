package com.panomc.plugins.pano.bungee.ui.main

import com.panomc.plugins.pano.bungee.BungeeMain
import com.panomc.plugins.pano.bungee.util.Config
import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenter
import com.panomc.plugins.pano.core.util.ConfigHelper
import javax.inject.Inject

class MainPresenterImpl : MainPresenter {
    @Inject
    lateinit var configHelper: ConfigHelper

    @Inject
    lateinit var serverEventPresenter: ServerEventPresenter

    init {
        BungeeMain.getComponent().inject(this)
    }

    private fun isPlatformConfigured() =
        !(configHelper as Config).getConfiguration().getString("platform.token").isNullOrEmpty()

    override fun onServerStart() {
        serverEventPresenter.onStart(isPlatformConfigured())
    }
}