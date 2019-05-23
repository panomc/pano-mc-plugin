package com.panomc.plugins.pano.core.di.module.presenter

import com.panomc.plugins.pano.core.ui.platformConnector.PlatformConnectorPresenter
import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenter
import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenterImpl
import com.panomc.plugins.pano.core.util.ScheduleHelper
import dagger.Module
import dagger.Provides
import java.util.logging.Logger
import javax.inject.Singleton

@Module
class ServerEventPresenterModule {

    @Provides
    @Singleton
    fun provideServerEventPresenter(
        logger: Logger,
        scheduleHelper: ScheduleHelper,
        platformConnectorPresenter: PlatformConnectorPresenter
    ): ServerEventPresenter = ServerEventPresenterImpl(logger, scheduleHelper, platformConnectorPresenter)
}