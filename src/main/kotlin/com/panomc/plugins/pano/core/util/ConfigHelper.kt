package com.panomc.plugins.pano.core.util

interface ConfigHelper {
    fun saveConfig()
    fun set(path: String, value: Any?)
    fun getString(path: String): String?
}