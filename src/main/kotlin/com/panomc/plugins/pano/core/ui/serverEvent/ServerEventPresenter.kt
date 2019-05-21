package com.panomc.plugins.pano.core.ui.serverEvent

interface ServerEventPresenter {
    fun onStart(isPlatformConfigured: Boolean)
}