package com.panomc.plugins.pano.core.di.module

import dagger.Module
import dagger.Provides
import org.bukkit.plugin.java.JavaPlugin
import javax.inject.Singleton

@Module
class SpigotPluginModule(@Singleton private val mPlugin: JavaPlugin) {

    @Provides
    @Singleton
    fun providePlugin() = mPlugin
}