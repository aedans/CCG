package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Game
import io.github.aedans.ccg.backend.Player
import io.github.aedans.server.simple.Connection
import io.github.aedans.server.simple.JsonConnection
import io.github.aedans.server.simple.read
import io.github.aedans.server.simple.write
import java.awt.BorderLayout

data class GameUI(val name1: String, val name2: String) : KFrame("Game between $name1 and $name2") {
    val hand = KHorizontalList()
    val mana = KVerticalList()

    init {
        add(hand, BorderLayout.SOUTH)
        add(mana, BorderLayout.EAST)
    }

    companion object {
        fun start(player1: Player, connection: Connection) {
            val connection2 = JsonConnection.create(connection)
            connection2.output.write(player1)
            val player2 = connection2.input.read<Player>()
            val connection1 = UIConnection(player1.name, GameUI(player1.name, player2.name))
            Game(connection1.combine(connection2)).run(listOf(player1, player2))
        }
    }
}
