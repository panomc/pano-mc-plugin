package com.panomc.plugins.panoCore.di.module

import com.panomc.plugins.panoCore.bungee.util.Config
import dagger.Module
import dagger.Provides
import net.md_5.bungee.config.Configuration
import javax.inject.Singleton

@Module
internal class BungeeConfigurationModule(@Singleton private val config: Config) {
    private val configuration: Configuration = config.getConfiguration()

    @Provides
    @Singleton
    fun provideConfiguration(): Configuration = configuration
}