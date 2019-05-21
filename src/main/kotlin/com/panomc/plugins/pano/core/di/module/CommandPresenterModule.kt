package com.panomc.plugins.pano.core.di.module

import com.panomc.plugins.pano.core.ui.command.CommandPresenter
import com.panomc.plugins.pano.core.ui.command.CommandPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommandPresenterModule {

    @Provides
    @Singleton
    fun provideCommandPresenter(): CommandPresenter = CommandPresenterImpl()
}