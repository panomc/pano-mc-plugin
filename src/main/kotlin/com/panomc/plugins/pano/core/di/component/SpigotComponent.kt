package com.panomc.plugins.pano.core.di.component

import com.panomc.plugins.pano.core.di.module.*
import com.panomc.plugins.pano.core.di.module.presenter.CommandPresenterModule
import com.panomc.plugins.pano.core.di.module.presenter.PlatformConnectorPresenterModule
import com.panomc.plugins.pano.core.di.module.presenter.ServerEventPresenterModule
import com.panomc.plugins.pano.core.di.module.spigot.SpigotPluginModule
import com.panomc.plugins.pano.spigot.ui.main.MainPresenterImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (LoggerModule::class),
        (ConfigModule::class),
        (VertxModule::class),
        (WebClientModule::class),
        (SpigotPluginModule::class),
        (CommandPresenterModule::class),
        (ServerEventPresenterModule::class),
        (PlatformConnectorPresenterModule::class),
        (ServerConfigurationModule::class),
        (ScheduleHelperModule::class)
    ]
)
interface SpigotComponent {
    fun inject(mainPresenterImpl: MainPresenterImpl)
}