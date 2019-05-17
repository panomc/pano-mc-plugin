package com.panomc.plugins.panoCore.di.component

import com.panomc.plugins.panoCore.bungee.BungeeMain
import com.panomc.plugins.panoCore.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(LoggerModule::class), (BungeeConfigurationModule::class), (BungeePluginModule::class), (VertxModule::class), (WebClientModule::class)])
internal interface BungeeComponent {
    fun inject(main: BungeeMain)
}