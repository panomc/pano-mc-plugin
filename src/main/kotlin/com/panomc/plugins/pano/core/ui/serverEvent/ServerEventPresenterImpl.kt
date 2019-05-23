package com.panomc.plugins.pano.core.ui.serverEvent

import com.panomc.plugins.pano.core.ui.platformConnector.PlatformConnectorPresenter
import com.panomc.plugins.pano.core.util.LanguageUtil
import com.panomc.plugins.pano.core.util.ScheduleHelper
import java.util.logging.Logger

class ServerEventPresenterImpl(
    private val mLogger: Logger,
    private val mScheduleHelper: ScheduleHelper,
    private val mPlatformConnectorPresenter: PlatformConnectorPresenter
) : ServerEventPresenter {
    override fun onStart(isPlatformConfigured: Boolean) {
        mLogger.info(LanguageUtil.format("&ePano started."))

        if (isPlatformConfigured) {
            mLogger.info(LanguageUtil.format("&6Started to connecting platform."))

            mScheduleHelper.startTask {
                mPlatformConnectorPresenter.connectPlatform()
            }
        } else {
            mLogger.severe(LanguageUtil.format("&cThis server has not been connected to any Pano Platform!"))
            mLogger.severe(LanguageUtil.format("""&6Type: "/pano connect <platform-address> <platform-code>" to connect Pano Platform."""))
            mLogger.severe(LanguageUtil.format("""&6For more information please visit: http://panomc.com/platform-connect"""))
        }
    }
}