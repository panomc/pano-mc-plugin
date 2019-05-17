package com.panomc.plugins.panoCore.di.module

import dagger.Module
import dagger.Provides
import org.bukkit.configuration.file.FileConfiguration
import javax.inject.Singleton

@Module
internal class SpigotConfigurationModule(@Singleton private val fileConfiguration: FileConfiguration) {

    @Provides
    @Singleton
    fun provideConfiguration() = fileConfiguration
}