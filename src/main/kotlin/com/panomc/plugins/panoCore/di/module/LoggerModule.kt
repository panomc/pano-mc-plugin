package com.panomc.plugins.panoCore.di.module

import dagger.Module
import dagger.Provides
import java.util.logging.Logger
import javax.inject.Singleton

@Module
internal class LoggerModule(private val logger: Logger) {

    @Provides
    @Singleton
    fun provideLogger() = logger
}