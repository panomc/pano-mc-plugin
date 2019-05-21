package com.panomc.plugins.pano.core.di.component

import com.panomc.plugins.pano.bungee.command.PanoCommand
import com.panomc.plugins.pano.bungee.ui.main.MainPresenterImpl
import com.panomc.plugins.pano.core.di.module.LoggerModule
import com.panomc.plugins.pano.core.di.module.VertxModule
import com.panomc.plugins.pano.core.di.module.WebClientModule
import com.panomc.plugins.pano.core.di.module.bungee.BungeeConfigurationModule
import com.panomc.plugins.pano.core.di.module.bungee.BungeePluginModule
import com.panomc.plugins.pano.core.di.module.presenter.CommandPresenterModule
import com.panomc.plugins.pano.core.di.module.presenter.ServerEventPresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (LoggerModule::class),
        (BungeeConfigurationModule::class),
        (BungeePluginModule::class),
        (VertxModule::class),
        (WebClientModule::class),
        (CommandPresenterModule::class),
        (ServerEventPresenterModule::class)
    ]
)
interface BungeeComponent {
    fun inject(mainPresenterImpl: MainPresenterImpl)

    fun inject(panoCommand: PanoCommand)
}