package io.github.aedans.ccg.backend

class Game(private val connections: Map<String, Connection>) {
    private fun write(string: String) = connections.values.forEach { it.write(string) }

    private fun Action.runAndWrite(players: Map<String, Player>): Map<String, Player> {
        flatten(players).forEach { write(it.asM()) }
        return run(players)
    }

    private fun Player.nextAction() = run {
        connections[name]!!.read()
    }

    fun run(players: Map<String, Player>) {
        var turn = 0
        @Suppress("NAME_SHADOWING") var players = players
        players.values.flatMap {
            listOf(
                Action.AddCardsToHand(it.name, it.starting),
                Action.Draw(it.name, 3),
                Action.AddMana(it.name, 1),
                Action.AddLife(it.name, 15)
            )
        }.forEach { players = it.runAndWrite(players) }
        while (true) {
            players.values.forEach { player ->
                var nextAction: Action
                do {
                    nextAction = Interpreter.value(player.nextAction()!!, Action.env)
                    players = nextAction.runAndWrite(players)
                } while (nextAction != Action.EndTurn)

                turn++
                if (turn % (players.size + 1) == 0)
                    players.values.map { Action.AddMana(it.name, 1) }.forEach { players = it.runAndWrite(players) }
            }

            players.values.map { Action.Draw(it.name, 1) }.forEach { players = it.runAndWrite(players) }
        }
    }
}
