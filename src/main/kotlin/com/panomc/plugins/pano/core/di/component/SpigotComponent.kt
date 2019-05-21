package com.panomc.plugins.pano.core.di.component

import com.panomc.plugins.pano.core.di.module.*
import com.panomc.plugins.pano.spigot.ui.main.MainPresenterImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (LoggerModule::class),
        (SpigotConfigurationModule::class),
        (VertxModule::class),
        (WebClientModule::class),
        (SpigotPluginModule::class),
        (CommandPresenterModule::class),
        (ServerEventPresenterModule::class)
    ]
)
interface SpigotComponent {
    fun inject(mainPresenterImpl: MainPresenterImpl)
}