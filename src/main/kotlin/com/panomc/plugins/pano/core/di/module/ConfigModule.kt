package com.panomc.plugins.pano.core.di.module

import com.panomc.plugins.pano.core.util.ConfigHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ConfigModule(@Singleton private val mConfigHelper: ConfigHelper) {

    @Provides
    @Singleton
    fun provideConfigHelper() = mConfigHelper
}