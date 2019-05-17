package com.panomc.plugins.panoCore.di.module

import dagger.Module
import dagger.Provides
import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import javax.inject.Singleton

@Module
internal class WebClientModule {
    @Provides
    @Singleton
    fun provideMysqlClient(vertx: Vertx) = WebClient.create(vertx, WebClientOptions())
}