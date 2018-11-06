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

    fun Player.addMana(i: Int = 1) = run {
        connection.addMana(name, i)
        copy(mana = mana + i)
    }

    fun Player.gainLife(i: Int = 1) = run {
        connection.gainLife(name, i)
        copy(life = life + 1)
    }

    fun run(players: List<Player>) {
        var players = players
        players = players.map { it.addToHand(it.starting) }
        players = players.map { it.draw(3) }
        players = players.map { it.addMana() }
        players = players.map { it.gainLife(15) }
        while (players.any { it.life >= 0 }) {
            
        }
    }
}
