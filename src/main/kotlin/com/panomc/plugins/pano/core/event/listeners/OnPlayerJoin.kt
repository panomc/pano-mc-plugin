package com.panomc.plugins.pano.core.event.listeners

import com.panomc.plugins.pano.core.PlatformEvent
import com.panomc.plugins.pano.core.PlatformManager
import com.panomc.plugins.pano.core.event.EventType
import com.panomc.plugins.pano.core.event.Listener
import com.panomc.plugins.pano.core.helper.EventHelper

class OnPlayerJoin(private val platformManager: PlatformManager) : Listener {
    override val eventType: EventType = EventType.ON_PLAYER_JOIN

    override fun handle(eventHelper: EventHelper, vararg args: Any) {
        val player = args[0]
        val playerData = eventHelper.convertToPlayerData(player)

        val eventRequest = platformManager.createEventRequest(PlatformEvent.ON_PLAYER_JOIN)

        eventRequest.put("player", playerData)

        platformManager.getWebSocket()?.writeTextMessage(eventRequest.encode())
    }
}