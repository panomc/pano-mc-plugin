package com.panomc.plugins.pano.core.di.component

import com.panomc.plugins.pano.bungee.command.PanoCommand
import com.panomc.plugins.pano.bungee.ui.main.MainPresenterImpl
import com.panomc.plugins.pano.core.di.module.*
import com.panomc.plugins.pano.core.di.module.bungee.BungeePluginModule
import com.panomc.plugins.pano.core.di.module.presenter.CommandPresenterModule
import com.panomc.plugins.pano.core.di.module.presenter.PlatformConnectorPresenterModule
import com.panomc.plugins.pano.core.di.module.presenter.ServerEventPresenterModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (LoggerModule::class),
        (ConfigModule::class),
        (BungeePluginModule::class),
        (VertxModule::class),
        (WebClientModule::class),
        (CommandPresenterModule::class),
        (ServerEventPresenterModule::class),
        (PlatformConnectorPresenterModule::class),
        (ServerConfigurationModule::class),
        (ScheduleHelperModule::class)
    ]
)
interface BungeeComponent {
    fun inject(mainPresenterImpl: MainPresenterImpl)

    fun inject(panoCommand: PanoCommand)
}