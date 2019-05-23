package com.panomc.plugins.pano.core.di.module.presenter

import com.panomc.plugins.pano.core.ui.platformConnector.PlatformConnectorPresenter
import com.panomc.plugins.pano.core.ui.platformConnector.PlatformConnectorPresenterImpl
import com.panomc.plugins.pano.core.util.ConfigHelper
import com.panomc.plugins.pano.core.util.ServerConfiguration
import dagger.Module
import dagger.Provides
import io.vertx.ext.web.client.WebClient
import java.util.logging.Logger
import javax.inject.Singleton

@Module
class PlatformConnectorPresenterModule {

    @Provides
    @Singleton
    fun providePlatformConnectorPresenter(
        webClient: WebClient,
        serverConfiguration: ServerConfiguration,
        configHelper: ConfigHelper,
        logger: Logger
    ): PlatformConnectorPresenter = PlatformConnectorPresenterImpl(webClient, serverConfiguration, configHelper, logger)
}