package com.panomc.plugins.pano.core.di.module.spigot

import dagger.Module
import dagger.Provides
import org.bukkit.configuration.file.FileConfiguration
import javax.inject.Singleton

@Module
class SpigotConfigurationModule(@Singleton private val mFileConfiguration: FileConfiguration) {

    @Provides
    @Singleton
    fun provideConfiguration() = mFileConfiguration
}