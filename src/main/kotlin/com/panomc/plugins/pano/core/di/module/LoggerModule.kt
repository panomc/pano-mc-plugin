package com.panomc.plugins.pano.core.di.module

import dagger.Module
import dagger.Provides
import java.util.logging.Logger
import javax.inject.Singleton

@Module
class LoggerModule(@Singleton private val mLogger: Logger) {

    @Provides
    @Singleton
    fun provideLogger() = mLogger
}