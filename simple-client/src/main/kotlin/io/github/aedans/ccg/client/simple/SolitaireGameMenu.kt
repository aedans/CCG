package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Card
import io.github.aedans.ccg.backend.Player
import io.github.aedans.server.simple.Server
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async

@UseExperimental(InternalCoroutinesApi::class)
class SolitaireGameMenu(private val mainMenu: MainMenu) : KMenuFrame("Solitaire Game") {
    private val portBox = KTextField("8080")
    private val deckBox1 = KTextField("Default")
    private val deckBox2 = KTextField("Default")
    private val submit = KButton("Submit") {
        val connection1 = Server.host(portBox.text.toInt())
        val connection2 = Server.join("localhost", portBox.text.toInt())
        connection1.invokeOnCompletion(onCancelling = true) {
            @Suppress("NestedLambdaShadowedImplicitParameter")
            if (it != null) isVisible = true
        }
        connection2.invokeOnCompletion(onCancelling = true) @Suppress("NestedLambdaShadowedImplicitParameter") {
            if (it != null) {
                isVisible = true
                KExceptionPopup(it)
            }
        }
        val deck2 = Deck.deck(deckBox1.text)
        val deck1 = Deck.deck(deckBox1.text)
        val player1 = Player(
            "player1",
            listOf(Card.card(deck1.starter1), Card.card(deck1.starter2), Card.card(deck1.starter3)),
            deck1.cards.map(Card.Companion::card),
            emptyList(),
            15
        )
        val player2 = Player(
            "player2",
            listOf(Card.card(deck2.starter1), Card.card(deck2.starter2), Card.card(deck2.starter3)),
            deck2.cards.map(Card.Companion::card),
            emptyList(),
            15
        )
        @Suppress("DeferredResultUnused")
        async { GameUI.start(player1, connection2.await()) }
        @Suppress("DeferredResultUnused")
        async { GameUI.start(player2, connection1.await()) }
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
