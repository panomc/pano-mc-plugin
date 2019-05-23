package com.panomc.plugins.pano.core.ui.platformConnector

import io.vertx.core.Future

interface PlatformConnectorPresenter {
    fun connectNewPlatform(platformAddress: String, platformCode: String): Future<String>

    fun connectPlatform(): Future<Any>
}