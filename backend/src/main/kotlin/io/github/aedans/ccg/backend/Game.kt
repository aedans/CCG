package io.github.aedans.ccg.backend

class Game(private val connection: ConnectionGroup) {
    fun Player.addToHand(cards: List<Card>) = run {
        connection.addToHand(name, cards)
        copy(hand = hand + cards)
    }

    fun Player.draw(i: Int = 0) = run {
        connection.draw(name, i)
        if (library.size <= i)
            copy(hand = hand + library, library = emptyList())
        else
            copy(hand = hand + library.take(i), library = library.drop(i))
    }

    fun run(players: List<Player>) {
        players
            .map { it.addToHand(it.starting) }
            .map { it.draw(3) }
    }
}
