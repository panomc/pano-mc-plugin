package com.panomc.plugins.pano.core.di.module.presenter

import com.panomc.plugins.pano.core.ui.command.CommandPresenter
import com.panomc.plugins.pano.core.ui.command.CommandPresenterImpl
import com.panomc.plugins.pano.core.ui.platformConnector.PlatformConnectorPresenter
import com.panomc.plugins.pano.core.util.ScheduleHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommandPresenterModule {

    @Provides
    @Singleton
    fun provideCommandPresenter(
        platformConnectorPresenter: PlatformConnectorPresenter,
        scheduleHelper: ScheduleHelper
    ): CommandPresenter = CommandPresenterImpl(platformConnectorPresenter, scheduleHelper)
}