package com.panomc.plugins.pano.core.di.module

import dagger.Module
import dagger.Provides
import io.vertx.core.Vertx
import javax.inject.Singleton

@Module
class VertxModule(@Singleton private val mVertx: Vertx) {

    @Provides
    @Singleton
    fun provideVertx() = mVertx
}