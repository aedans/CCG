package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Card
import io.github.aedans.ccg.backend.ConnectionGroup

class UIConnection(val name: String, val gameUI: GameUI) : ConnectionGroup {
    override fun addToHand(name: String, cards: List<Card>) {
        if (name == this.name) {
            cards.forEach { gameUI.hand.add(CardComponent(it)) }
            gameUI.pack()
        }
    }

    override fun draw(name: String, cards: List<Card>) {
        if (name == this.name) {
            cards.forEach { gameUI.hand.add(CardComponent(it)) }
            gameUI.pack()
        }
    }

    override fun addMana(name: String, i: Int) {
        if (name == this.name) {
            (0 until i).forEach {
                gameUI.mana.add(Mana())
                gameUI.pack()
            }
        }
    }

    override fun gainLife(name: String, i: Int) {
        if (name == this.name) {
            gameUI.life.text = (gameUI.life.text.toInt() + i).toString()
            gameUI.pack()
        }
    }
}
