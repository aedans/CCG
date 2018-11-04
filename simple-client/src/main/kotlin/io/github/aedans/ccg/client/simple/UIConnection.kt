package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Card
import io.github.aedans.ccg.backend.ConnectionGroup

class UIConnection(val name: String, val gameUI: GameUI) : ConnectionGroup {
    override fun addToHand(name: String, cards: List<Card>) {
        if (name == this.name) {
            cards.forEach { gameUI.hand.add(CardComponent(it)) }
        }
    }

    override fun draw(name: String, cards: List<Card>) {
        if (name == this.name) {
            cards.forEach { gameUI.hand.add(CardComponent(it)) }
        }
    }
}
