package com.panomc.plugins.pano.core.di.module

import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenter
import com.panomc.plugins.pano.core.ui.serverEvent.ServerEventPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServerEventPresenterModule {

    @Provides
    @Singleton
    fun provideServerEventPresenter(): ServerEventPresenter = ServerEventPresenterImpl()
}