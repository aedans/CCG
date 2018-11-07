package io.github.aedans.ccg.backend

class Game(private val connections: Map<String, Connection>) {
    private fun write(string: String) = connections.values.forEach { it.write(string) }

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
                Action.AddMaxMana(it.name, 1),
                Action.AddLife(it.name, 15)
            )
        }.forEach { players = it.run(players) { write(it) } }
        while (true) {
            for (key in players.keys) {
                fun player() = players[key]!!

                players = Action.AddCurrentMana(player().name, player().maxMana - player().currentMana).run(players) { write(it) }

                var nextAction: Action
                do {
                    nextAction = Interpreter.value(player().nextAction()!!, Action.env)
                    players = nextAction.run(players) { write(it) }
                } while (nextAction != Action.EndTurn)

                turn++
                if (turn % (players.size + 1) == 0)
                    players.values.map { Action.AddMaxMana(it.name, 1) }.forEach { players = it.run(players) { write(it) } }
            }

            players.values.map { Action.Draw(it.name, 1) }.forEach { players = it.run(players) { write(it) } }
        }
    }
}
