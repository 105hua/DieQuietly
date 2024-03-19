/* Licensed under GNU General Public License v3.0 */
package joshdev.diequietly

import joshdev.diequietly.commands.ToggleDeathMsgs
import joshdev.diequietly.events.PlayerDeath
import joshdev.diequietly.libs.DatabaseWrapper
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class DieQuietly : JavaPlugin() {
    companion object {
        lateinit var pluginLogger: Logger
        lateinit var wrapper: DatabaseWrapper
    }

    override fun onEnable() {
        // Set companion objects.
        pluginLogger = logger
        // Setup data folder and wrapper.
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        val dataFolderPath = dataFolder.absolutePath
        wrapper = DatabaseWrapper(dataFolderPath)
        // Setup event.
        server.pluginManager.registerEvents(PlayerDeath(), this)
        // Setup toggle command.
        getCommand("toggledeathmsgs")?.setExecutor(ToggleDeathMsgs())
        // Print plugin ready msg to console.
        pluginLogger.info("DieQuietly is now ready!")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
