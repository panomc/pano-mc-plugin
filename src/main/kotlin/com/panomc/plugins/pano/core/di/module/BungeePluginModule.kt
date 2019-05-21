package com.panomc.plugins.pano.core.di.module

import dagger.Module
import dagger.Provides
import net.md_5.bungee.api.plugin.Plugin
import javax.inject.Singleton

@Module
class BungeePluginModule(@Singleton private val mPlugin: Plugin) {

    @Provides
    @Singleton
    fun providePlugin() = mPlugin
}