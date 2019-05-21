package com.panomc.plugins.pano.core.ui.serverEvent

import java.util.logging.Logger

interface ServerEventPresenter {
    fun onStart(isPlatformConfigured: Boolean, logger: Logger)
}