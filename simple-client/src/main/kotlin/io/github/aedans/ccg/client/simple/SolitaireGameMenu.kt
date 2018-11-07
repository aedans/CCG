package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Card
import io.github.aedans.ccg.backend.Game
import io.github.aedans.ccg.backend.Player
import kotlinx.coroutines.InternalCoroutinesApi

@UseExperimental(InternalCoroutinesApi::class)
class SolitaireGameMenu(private val mainMenu: MainMenu) : KMenuFrame("Solitaire Game") {
    private val deckBox1 = KTextField("Default1")
    private val deckBox2 = KTextField("Default2")
    private val submit = KButton("Submit") {
        val deck2 = Deck.deck(deckBox1.text)
        val deck1 = Deck.deck(deckBox1.text)
        val player1 = Player(
            "player1",
            listOf(Card.card(deck1.starter1), Card.card(deck1.starter2), Card.card(deck1.starter3)),
            deck1.cards.map(Card.Companion::card)
        )
        val player2 = Player(
            "player2",
            listOf(Card.card(deck2.starter1), Card.card(deck2.starter2), Card.card(deck2.starter3)),
            deck2.cards.map(Card.Companion::card)
        )
        isVisible = false
        val ui1 = GameUI(player1.name)
        val ui2 = GameUI(player2.name)
        Game(mapOf(player1.name to ui1.connection(), player2.name to ui2.connection()))
            .run(mapOf(player1.name to player1, player2.name to player2))
    }

    private val cancel = KButton("Cancel") {
        mainMenu.isVisible = true
        isVisible = false
    }

    init {
        addAll(deckBox1, deckBox2, submit, cancel)
        pack()
    }
}
