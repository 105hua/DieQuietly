/* Licensed under GNU General Public License v3.0 */
package joshdev.diequietly.events

import joshdev.diequietly.DieQuietly
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeath : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val deathComponent = event.deathMessage()
        event.deathMessage(null) // Nullify to stop the death message showing to everyone.
        if (deathComponent != null) {
            val deathMsg = PlainTextComponentSerializer.plainText().serialize(deathComponent)
            DieQuietly.pluginLogger.info(deathMsg)
            event.deathMessage(null)
            val onlinePlayers = event.player.server.onlinePlayers
            for (player in onlinePlayers) {
                val isToggled = DieQuietly.wrapper.getPlayerToggle(player.uniqueId.toString())
                if (!isToggled) {
                    player.sendMessage(deathMsg)
                }
            }
        } else {
            DieQuietly.pluginLogger.info("Death message was null.")
        }
    }
}
