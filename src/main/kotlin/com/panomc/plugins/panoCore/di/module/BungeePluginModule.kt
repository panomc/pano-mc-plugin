package com.panomc.plugins.panoCore.di.module

import dagger.Module
import dagger.Provides
import net.md_5.bungee.api.plugin.Plugin
import javax.inject.Singleton

@Module
internal class BungeePluginModule(@Singleton private val plugin: Plugin) {

    @Provides
    @Singleton
    fun providePlugin() = plugin
}