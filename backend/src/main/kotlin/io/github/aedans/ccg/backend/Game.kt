package io.github.aedans.ccg.backend

import io.github.aedans.ccg.backend.MRep.Companion.string

class Game(private val connections: Map<String, Connection>) {
    private fun write(string: String) = connections.values.forEach { it.write(string) }

    private fun Player.addToHand(cards: List<Card>) = run {
        write("(add-to-hand ${string(name)} ${string(cards.map { string(it) })})")
        copy(hand = hand + cards)
    }

    private fun Player.draw(i: Int = 1) = run {
        val number = if (library.size <= i) library.size else i
        val cards = library.take(number)
        write("(draw ${string(name)} ${string(cards.map { string(it) })})")
        copy(hand = hand + cards, library = library.drop(number))
    }

    private fun Player.addMana(i: Int = 1) = run {
        write("(add-mana ${string(name)} ${string(i)})")
        copy(mana = mana + i)
    }

    private fun Player.gainLife(i: Int = 1) = run {
        write("(gain-life ${string(name)} ${string(i)})")
        copy(life = life + 1)
    }

    private fun Player.nextAction() = run {
        connections[name]!!.read()
    }

    fun run(players: List<Player>) {
        var turn = 0
        @Suppress("NAME_SHADOWING") var players = players
        players = players.map { it.addToHand(it.starting) }
        players = players.map { it.draw(3) }
        players = players.map { it.addMana() }
        players = players.map { it.gainLife(15) }
        while (true) {
            players.forEach { player ->
                turn++
                var nextAction: String
                do {
                    nextAction = player.nextAction()!!
                    println(nextAction)
                } while (nextAction != "null")
                if (turn % (players.size + 1) == 0) players.map { it.addMana() }
            }
            players.map { it.draw() }
        }
    }
}
