package com.panomc.plugins.pano.bungee.util

import com.google.common.io.ByteStreams
import com.panomc.plugins.pano.core.util.ConfigHelper
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.logging.Logger

class Config(private val mDataFolder: File, private val mLogger: Logger, private val mConfigFileInJar: InputStream) :
    ConfigHelper {
    private lateinit var mConfiguration: Configuration

    init {
        if (!isDataFolderExists() && !isDataFolderSuccessfullyCreated())
            sendUnableToLoadConfigFileErrorMessage("Plugin folder couldn't be created or loaded")

        val configFile = getConfigFile()

        if (!configFile.exists() && !createConfigFileSuccess(configFile))
            sendUnableToLoadConfigFileErrorMessage("Config can't created or loaded")
        else
            mConfiguration = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(configFile)
    }

    private fun getConfigFile(): File {
        return File(mDataFolder, "config.yml")
    }

    fun getConfiguration(): Configuration {
        return mConfiguration
    }

    private fun sendUnableToLoadConfigFileErrorMessage(cause: String) {
        mLogger.severe("Unable to load config file. Because: $cause")
    }

    private fun isDataFolderExists(): Boolean {
        return this.mDataFolder.exists()
    }

    private fun isDataFolderSuccessfullyCreated(): Boolean {
        return mDataFolder.mkdir()
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
                    ByteStreams.copy(this.mConfigFileInJar, out)

                } catch (localThrowable1: Throwable) {
                    throw localThrowable1
                }

            } catch (localThrowable2: Throwable) {
                localThrowable4 = localThrowable2
                throw localThrowable2

            } finally {
                if (localThrowable4 != null) {
                    try {
                        mConfigFileInJar.close()

                    } catch (localThrowable3: Throwable) {
                        throw localThrowable3
                    }
                } else {
                    this.mConfigFileInJar.close()
                }
            }

            return true
        }
    }

    override fun set(path: String, value: Any?) {
        mConfiguration.set(path, value)
    }

    override fun saveConfig() {
        ConfigurationProvider.getProvider(YamlConfiguration::class.java)
            .save(mConfiguration, File(mDataFolder, "config.yml"))
    }

    override fun getString(path: String): String? = mConfiguration.getString(path)
}