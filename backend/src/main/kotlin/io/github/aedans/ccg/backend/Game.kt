package io.github.aedans.ccg.backend

class Game(private val connection: ConnectionGroup) {
    fun Player.addToHand(cards: List<Card>) = run {
        connection.addToHand(name, cards)
        copy(hand = hand + cards)
    }

    fun Player.draw(i: Int = 0) = run {
        val number = if (library.size <= i) library.size else i
        connection.draw(name, library.take(number))
        copy(hand = hand + library.take(number), library = library.drop(number))
    }

    fun run(players: List<Player>) {
        players
            .map { it.addToHand(it.starting) }
            .map { it.draw(3) }
    }
}
