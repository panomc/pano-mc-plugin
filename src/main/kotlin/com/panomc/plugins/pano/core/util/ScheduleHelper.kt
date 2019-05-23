package com.panomc.plugins.pano.core.util

interface ScheduleHelper {
    fun startTask(callback: () -> Unit)

    fun stopTask()
}