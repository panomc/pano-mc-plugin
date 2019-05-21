package com.panomc.plugins.pano.core.ui.serverEvent

import com.panomc.plugins.pano.core.util.LanguageUtil
import java.util.logging.Logger

class ServerEventPresenterImpl(private val mLogger: Logger) : ServerEventPresenter {
    override fun onStart(isPlatformConfigured: Boolean) {
        mLogger.info(LanguageUtil.format("&ePano started."))

        if (isPlatformConfigured) {
            mLogger.info(LanguageUtil.format("&6Started to connecting platform."))

            mLogger.info("baglanmis")
        } else {
            mLogger.severe(LanguageUtil.format("&cThis server has not been connected to any Pano Platform!"))
            mLogger.severe(LanguageUtil.format("""&6Type: "/pano connect <platform-address> <platform-code>" to connect Pano Platform."""))
            mLogger.severe(LanguageUtil.format("""&6For more information please visit: http://panomc.com/platform-connect"""))
        }
    }
}