package com.panomc.plugins.pano.core.di.module

import com.panomc.plugins.pano.bungee.util.Config
import dagger.Module
import dagger.Provides
import net.md_5.bungee.config.Configuration
import javax.inject.Singleton

@Module
class BungeeConfigurationModule(@Singleton private val mConfig: Config) {
    @Singleton
    private val mConfiguration by lazy {
        mConfig.getConfiguration()
    }

    @Provides
    @Singleton
    fun provideConfiguration(): Configuration = mConfiguration
}