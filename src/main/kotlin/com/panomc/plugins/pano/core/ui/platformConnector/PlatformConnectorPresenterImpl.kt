package com.panomc.plugins.pano.core.ui.platformConnector

import ch.jamiete.mcping.MinecraftPing
import ch.jamiete.mcping.MinecraftPingOptions
import com.panomc.plugins.pano.core.util.ConfigHelper
import com.panomc.plugins.pano.core.util.LanguageUtil
import com.panomc.plugins.pano.core.util.ServerConfiguration
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import java.util.logging.Logger

class PlatformConnectorPresenterImpl(
    private val mWebClient: WebClient,
    private val mServerConfiguration: ServerConfiguration,
    private val mConfigHelper: ConfigHelper,
    private val mLogger: Logger
) : PlatformConnectorPresenter {
    private var mPlatformConnectedStatus = false

    override fun connectPlatform() =
        Future.future<Any> { connectPlatform ->
            mWebClient
                .post(
                    mConfigHelper.getString("platform.address"),
                    "/api/server/connect"
                )
                .sendJsonObject(
                    JsonObject()
                        .put("token", mConfigHelper.getString("platform.token"))
                        .put("serverName", mServerConfiguration.serverName)
                        .put("playerCount", mServerConfiguration.getPlayerCount())
                        .put("maxPlayerCount", mServerConfiguration.getMaxPlayerCount())
                        .put("serverType", mServerConfiguration.serverType)
                        .put("serverVersion", mServerConfiguration.serverVersion)
                ) { apiRequest ->
                    if (apiRequest.succeeded()) {
                        try {
                            val response = apiRequest.result()
                            val body = response.bodyAsJsonObject()

                            val result = body.getString("result")

                            if (result == "NEED_PERMISSION") {
                                mLogger.severe(LanguageUtil.translateColor("&cError: Need permission. Please give permission on panel of Pano Platform to this server."))
                                mPlatformConnectedStatus = false
                            } else if (result == "ok" && !mPlatformConnectedStatus) {
                                mPlatformConnectedStatus = true

                                mLogger.info(LanguageUtil.translateColor("&2Successfully connected to Pano Platform."))
                            }
                        } catch (exception: Exception) {
                            mLogger.severe(LanguageUtil.translateColor("&cError: Failed to connect Pano Platform."))
                        }
                    } else {
                        mLogger.severe(LanguageUtil.translateColor("&cError: Failed to connect Pano Platform."))
                        mPlatformConnectedStatus = false
                    }

                    connectPlatform.complete()
                }
        }

    override fun connectNewPlatform(platformAddress: String, platformCode: String) =
        Future.future<String> { connectNewPlatform ->
            val pingData = MinecraftPing().getPing(
                MinecraftPingOptions().setHostname(mServerConfiguration.hostAddress).setPort(mServerConfiguration.port)
            )

            mWebClient
                .post(
                    platformAddress,
                    "/api/server/connectNew"
                )
                .sendJsonObject(
                    JsonObject()
                        .put("platformCode", platformCode)
                        .put("favicon", pingData.favicon ?: "null")
                        .put("serverName", mServerConfiguration.serverName)
                        .put("playerCount", mServerConfiguration.getPlayerCount())
                        .put("maxPlayerCount", mServerConfiguration.getMaxPlayerCount())
                        .put("serverType", mServerConfiguration.serverType)
                        .put("serverVersion", mServerConfiguration.serverVersion)
                ) { apiRequest ->
                    if (apiRequest.succeeded()) {
                        val result = try {
                            val response = apiRequest.result()
                            val body = response.bodyAsJsonObject()

                            val result =
                                if (body.getString("result") == "ok")
                                    "ok"
                                else
                                    body.getString("error")

                            if (result == "ok") {
                                val token = body.getString("token")

                                savePlatform(platformAddress, token)
                            }

                            result
                        } catch (exception: Exception) {
                            "FAILED_TO_CONNECT"
                        }

                        connectNewPlatform.complete(result)
                    } else
                        connectNewPlatform.complete("FAILED_TO_CONNECT")
                }
        }

    private fun savePlatform(platformAddress: String, token: String) {
        mConfigHelper.set("platform.address", platformAddress)
        mConfigHelper.set("platform.token", token)

        mConfigHelper.saveConfig()
    }
}