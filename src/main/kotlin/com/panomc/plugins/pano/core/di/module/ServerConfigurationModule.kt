package com.panomc.plugins.pano.core.di.module

import com.panomc.plugins.pano.core.util.ServerConfiguration
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServerConfigurationModule(@Singleton private val mServerConfiguration: ServerConfiguration) {
    @Provides
    @Singleton
    fun provideServerConfiguration() = mServerConfiguration
}