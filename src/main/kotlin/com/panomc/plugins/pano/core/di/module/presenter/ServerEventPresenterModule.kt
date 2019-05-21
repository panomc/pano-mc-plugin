package com.panomc.plugins.pano.core.di.module.presenter

import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenter
import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenterImpl
import dagger.Module
import dagger.Provides
import java.util.logging.Logger
import javax.inject.Singleton

@Module
class ServerEventPresenterModule {

    @Provides
    @Singleton
    fun provideServerEventPresenter(logger: Logger): ServerEventPresenter = ServerEventPresenterImpl(logger)
}