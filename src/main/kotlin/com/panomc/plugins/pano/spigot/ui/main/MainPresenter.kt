package com.panomc.plugins.pano.spigot.ui.main

import org.bukkit.command.CommandSender

interface MainPresenter {
    fun onServerStart()
    fun onPanoCommand(sender: CommandSender, args: Array<String>)
}