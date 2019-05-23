package com.panomc.plugins.pano.core.ui.command

import com.panomc.plugins.pano.core.ui.platformConnector.PlatformConnectorPresenter
import com.panomc.plugins.pano.core.util.CommandPresenterHelper
import com.panomc.plugins.pano.core.util.ScheduleHelper

class CommandPresenterImpl(
    private val mPlatformConnectorPresenter: PlatformConnectorPresenter,
    private val mScheduleHelper: ScheduleHelper
) : CommandPresenter {
    override fun onPanoCommand(
        commandSender: Any,
        commandPresenterHelper: CommandPresenterHelper,
        args: Array<String>
    ) {
        if (args.isEmpty())
            showHelp(commandSender, commandPresenterHelper)
        else if (args[0].equals("connect", true))
            if (args.size > 2) {
                val platformAddress = args[1]
                val platformCode = args[2]

                commandPresenterHelper.sendMessage(commandSender, "&2Connecting...")

                mPlatformConnectorPresenter.connectNewPlatform(platformAddress, platformCode)
                    .setHandler { connectNewPlatform ->
                        if (connectNewPlatform.result() == "ok") {
                            commandPresenterHelper.sendMessage(
                                commandSender,
                                "&eToken saved, please give permission on panel of Pano Platform to this server."
                            )

                            mScheduleHelper.startTask {
                                mPlatformConnectorPresenter.connectPlatform()
                            }
                        } else if (connectNewPlatform.result() == "FAILED_TO_CONNECT")
                            commandPresenterHelper.sendMessage(
                                commandSender,
                                "&cCouldn't connect to Pano Platform. Check your information."
                            )
                        else
                            commandPresenterHelper.sendMessage(
                                commandSender,
                                "&c${connectNewPlatform.result()}"
                            )
                    }
            } else
                showConnectArgumentUsage(commandSender, commandPresenterHelper)
        else
            showHelp(commandSender, commandPresenterHelper)
    }

    private fun showConnectArgumentUsage(
        commandSender: Any,
        commandPresenterHelper: CommandPresenterHelper
    ) {
        commandPresenterHelper.sendMessage(commandSender, "&cUsage: /pano connect <platform-address> <platform-code>")
    }

    private fun showHelp(
        commandSender: Any,
        commandPresenterHelper: CommandPresenterHelper
    ) {
        commandPresenterHelper.sendMessage(commandSender, "&6Pano Web Platform Plugin Commands:")
        commandPresenterHelper.sendMessage(
            commandSender,
            "&e/pano connect <platform-address> <platform-code> - Connect to Pano platform."
        )
    }
}