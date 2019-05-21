package com.panomc.plugins.pano.core.di.module

import dagger.Module
import dagger.Provides
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import javax.inject.Singleton

@Module
class WebClientModule {
    @Provides
    @Singleton
    fun provideMysqlClient(vertx: Vertx) = WebClient.create(vertx, WebClientOptions())
}