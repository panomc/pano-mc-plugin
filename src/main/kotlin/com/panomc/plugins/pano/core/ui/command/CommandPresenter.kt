package com.panomc.plugins.pano.core.ui.command

import com.panomc.plugins.pano.core.util.CommandPresenterHelper

interface CommandPresenter {
    fun onPanoCommand(commandSender: Any, commandPresenterHelper: CommandPresenterHelper, args: Array<String>)
}