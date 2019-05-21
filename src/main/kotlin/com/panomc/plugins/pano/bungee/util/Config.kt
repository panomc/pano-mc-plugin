package com.panomc.plugins.pano.bungee.util

import com.google.common.io.ByteStreams
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.logging.Logger

class Config(private val dataFolder: File, private val logger: Logger, private val configFileInJar: InputStream) {
    private lateinit var configuration: Configuration

    init {
        if (!isDataFolderExists() && !isDataFolderSuccessfullyCreated())
            sendUnableToLoadConfigFileErrorMessage("Plugin folder couldn't be created or loaded")

        val configFile = getConfigFile()

        if (!configFile.exists() && !createConfigFileSuccess(configFile))
            sendUnableToLoadConfigFileErrorMessage("Config can't created or loaded")
        else
            configuration = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(configFile)
    }

    private fun getConfigFile(): File {
        return File(dataFolder, "config.yml")
    }

    fun getConfiguration(): Configuration {
        return configuration
    }

    private fun sendUnableToLoadConfigFileErrorMessage(cause: String) {
        logger.severe("Unable to load config file. Because: $cause")
    }

    private fun isDataFolderExists(): Boolean {
        return this.dataFolder.exists()
    }

    private fun isDataFolderSuccessfullyCreated(): Boolean {
        return dataFolder.mkdir()
    }

    private fun createConfigFileSuccess(configFile: File): Boolean {
        if (!configFile.createNewFile()) {
            sendUnableToLoadConfigFileErrorMessage("Can't create config file")

            return false

        } else {
            var localThrowable4: Throwable? = null

            try {
                val out = FileOutputStream(configFile)

                try {
                    ByteStreams.copy(this.configFileInJar, out)

                } catch (localThrowable1: Throwable) {
                    throw localThrowable1
                }

            } catch (localThrowable2: Throwable) {
                localThrowable4 = localThrowable2
                throw localThrowable2

            } finally {
                if (localThrowable4 != null) {
                    try {
                        configFileInJar.close()

                    } catch (localThrowable3: Throwable) {
                        throw localThrowable3
                    }
                } else {
                    this.configFileInJar.close()
                }
            }

            return true
        }
    }
}