/* Licensed under GNU General Public License v3.0 */
package joshdev.diequietly.commands

import joshdev.diequietly.DieQuietly
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

class ToggleDeathMsgs : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<out String>?,
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("You must be a player to use this command.")
            return true
        }
        val player: Player = sender
        if (player.hasPermission("diequietly.toggle")) {
            val persistentValue = player.persistentDataContainer.get(DieQuietly.toggleKey, PersistentDataType.BOOLEAN)
            val newValue: Boolean =
                if (persistentValue == null) {
                    true
                } else {
                    !persistentValue
                }
            if (!newValue) {
                val shownComponent =
                    Component.text(
                        "Death messages are now shown.",
                        NamedTextColor.AQUA,
                    )
                player.sendMessage(shownComponent)
            } else {
                val hiddenComponent =
                    Component.text(
                        "Death messages are now hidden.",
                        NamedTextColor.AQUA,
                    )
                player.sendMessage(hiddenComponent)
            }
            player.persistentDataContainer.set(DieQuietly.toggleKey, PersistentDataType.BOOLEAN, newValue)
        } else {
            val noPermissionComponent =
                Component.text(
                    "You do not have permission to use this command.",
                    NamedTextColor.RED,
                )
            player.sendMessage(noPermissionComponent)
        }
        return true
    }
}
