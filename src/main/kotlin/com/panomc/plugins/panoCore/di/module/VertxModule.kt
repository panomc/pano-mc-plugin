package com.panomc.plugins.panoCore.di.module

import dagger.Module
import dagger.Provides
import io.vertx.core.Vertx
import javax.inject.Singleton

@Module
internal class VertxModule(private val vertx: Vertx) {

    @Provides
    @Singleton
    fun provideVertx() = vertx
}