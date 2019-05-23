package com.panomc.plugins.pano.bungee.command

import com.panomc.plugins.pano.bungee.BungeeMain
import com.panomc.plugins.pano.core.ui.command.CommandPresenter
import com.panomc.plugins.pano.core.util.CommandPresenterHelper
import com.panomc.plugins.pano.core.util.LanguageUtil
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import javax.inject.Inject

class PanoCommand : Command("Pano", "pano.admin"), CommandPresenterHelper {
    @Inject
    lateinit var commandPresenter: CommandPresenter

    init {
        BungeeMain.getComponent().inject(this)
    }

    override fun execute(sender: CommandSender, args: Array<String>) {
        commandPresenter.onPanoCommand(sender, this, args)
    }

    override fun sendMessage(commandSender: Any, message: String) {
        (commandSender as CommandSender).sendMessage(TextComponent(LanguageUtil.translateColor(message)))
    }
}