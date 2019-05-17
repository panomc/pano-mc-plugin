package com.panomc.plugins.panoCore.di.component

import com.panomc.plugins.panoCore.di.module.LoggerModule
import com.panomc.plugins.panoCore.di.module.SpigotConfigurationModule
import com.panomc.plugins.panoCore.di.module.VertxModule
import com.panomc.plugins.panoCore.di.module.WebClientModule
import com.panomc.plugins.panoCore.spigot.SpigotMain
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(LoggerModule::class), (SpigotConfigurationModule::class), (VertxModule::class), (WebClientModule::class)])
internal interface SpigotComponent {
    fun inject(main: SpigotMain)
}