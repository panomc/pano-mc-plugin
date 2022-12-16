package com.panomc.plugins.pano.spigot

import com.panomc.plugins.pano.core.command.Command
import com.panomc.plugins.pano.core.helper.CommandHelper
import com.panomc.plugins.pano.core.util.McTextUtil
import kotlinx.coroutines.runBlocking
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand

class SpigotCommand(private val command: Command) : BukkitCommand(command.name), CommandHelper {
    override fun sendMessage(commandSender: Any, message: String) {
        (commandSender as CommandSender).sendMessage(McTextUtil.translateColor(message))
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        return runBlocking {
            return@runBlocking command.handler(sender, args, this@SpigotCommand)
        }
    }
}