/* Licensed under GNU General Public License v3.0 */
package joshdev.diequietly.libs

import java.sql.Connection
import java.sql.DriverManager

class DatabaseWrapper(dataFolderPath: String) {
    private var dbConnection: Connection = DriverManager.getConnection("jdbc:sqlite:$dataFolderPath/toggles.db")

    init {
        val stmt = dbConnection.createStatement()
        stmt.execute("CREATE TABLE IF NOT EXISTS toggles (uuid TEXT PRIMARY KEY, toggle BOOLEAN)")
    }

    fun getPlayerToggle(uuid: String): Boolean {
        val stmt = dbConnection.prepareStatement("SELECT toggle FROM toggles WHERE uuid = ?")
        stmt.setString(1, uuid)
        val rs = stmt.executeQuery()
        return rs.getBoolean("toggle")
    }

    fun togglePlayer(uuid: String): Boolean {
        val newValue = !getPlayerToggle(uuid)
        val stmt = dbConnection.prepareStatement("INSERT OR REPLACE INTO toggles (uuid, toggle) VALUES (?, ?)")
        stmt.setString(1, uuid)
        stmt.setBoolean(2, newValue)
        stmt.executeUpdate()
        return newValue
    }
}
