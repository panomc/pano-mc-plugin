package com.panomc.plugins.pano.core.ui.serverEvent

import com.panomc.plugins.pano.core.util.LanguageUtil
import java.util.logging.Logger

class ServerEventPresenterImpl : ServerEventPresenter {
    override fun onStart(isPlatformConfigured: Boolean, logger: Logger) {
        logger.info(LanguageUtil.format("&ePano started."))

        if (isPlatformConfigured) {
            logger.info(LanguageUtil.format("&6Started to connecting platform."))

            logger.info("baglanmis")
        } else {
            logger.severe(LanguageUtil.format("&cThis server has not been connected to any Pano Platform!"))
            logger.severe(LanguageUtil.format("""&6Type: "/pano connect <platform-address> <platform-code>" to connect Pano Platform."""))
            logger.severe(LanguageUtil.format("""&6For more information please visit: http://panomc.com/platform-connect"""))
        }
    }
}