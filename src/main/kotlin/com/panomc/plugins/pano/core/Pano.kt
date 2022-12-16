package com.panomc.plugins.pano.core

import com.panomc.plugins.pano.core.annotation.Boot
import com.panomc.plugins.pano.core.command.CommandManager
import com.panomc.plugins.pano.core.config.ConfigManager
import com.panomc.plugins.pano.core.helper.PanoPluginMain
import com.panomc.plugins.pano.core.schedule.ScheduleManager
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.AnnotationConfigApplicationContext


@Boot
class Pano(private val panoPluginMain: PanoPluginMain) : CoroutineVerticle() {
    companion object {
        private val options by lazy {
            VertxOptions()
        }

        private val vertx by lazy {
            Vertx.vertx(options)
        }

        val VERSION = "1.0.0"

        internal fun init(panoPluginMain: PanoPluginMain): Pano {
            val pano = Pano(panoPluginMain)

            runBlocking {
                vertx.deployVerticle(pano).await()
            }

            return pano
        }
    }

    private lateinit var applicationContext: AnnotationConfigApplicationContext
    private lateinit var configManager: ConfigManager
    private lateinit var commandManager: CommandManager
    private lateinit var scheduleManager: ScheduleManager
    private lateinit var platformManager: PlatformManager
    private val logger = panoPluginMain.getLogger()

    override suspend fun start() {
        println(
            "\n" +
                    " ______   ______     __   __     ______    \n" +
                    "/\\  == \\ /\\  __ \\   /\\ \"-.\\ \\   /\\  __ \\   \n" +
                    "\\ \\  _-/ \\ \\  __ \\  \\ \\ \\-.  \\  \\ \\ \\/\\ \\  \n" +
                    " \\ \\_\\    \\ \\_\\ \\_\\  \\ \\_\\\\\"\\_\\  \\ \\_____\\ \n" +
                    "  \\/_/     \\/_/\\/_/   \\/_/ \\/_/   \\/_____/  v${VERSION}\n" +
                    "                                           "
        )

        logger.info("Initializing Pano Core")

        init()
    }

    override suspend fun stop() {
        logger.info("Disabling Pano")

        commandManager.disable()

        scheduleManager.disable()

        platformManager.stop()

        super.stop()

        logger.info("Pano is disabled")
    }

    internal fun onServerStart() {
        platformManager.start()
    }

    internal fun disable() {
        runBlocking {
            stop()
        }
    }

    private suspend fun init() {
        initDependencyInjection()

        initConfigManager()

        initCommandManager()
    }

    private fun initDependencyInjection() {
        logger.info("Initializing dependency injection")

        SpringConfig.setDefaults(vertx, panoPluginMain)

        applicationContext = AnnotationConfigApplicationContext(SpringConfig::class.java)
        scheduleManager = applicationContext.getBean(ScheduleManager::class.java)
        platformManager = applicationContext.getBean(PlatformManager::class.java)
    }

    private suspend fun initConfigManager() {
        logger.info("Initializing config manager")

        configManager = applicationContext.getBean(ConfigManager::class.java)

        try {
            configManager.init()
        } catch (e: Exception) {
            println(e)
        }
    }

    private fun initCommandManager() {
        logger.info("Initializing command manager")

        commandManager = applicationContext.getBean(CommandManager::class.java)

        commandManager.init()
    }
}