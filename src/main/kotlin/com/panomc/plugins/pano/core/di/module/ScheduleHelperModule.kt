package com.panomc.plugins.pano.core.di.module

import com.panomc.plugins.pano.core.util.ScheduleHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ScheduleHelperModule(@Singleton private val mScheduleHelper: ScheduleHelper) {
    @Provides
    @Singleton
    fun provideScheduleHelper() = mScheduleHelper
}