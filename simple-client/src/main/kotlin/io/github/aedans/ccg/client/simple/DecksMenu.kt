package io.github.aedans.ccg.client.simple

import java.io.File

class DecksMenu(private val mainMenu: MainMenu) : KMenuFrame("Decks") {
    private val newDeck = KButton("New Deck") {
        DeckUI(mainMenu, Deck("untitled", emptyList()))
        isVisible = false
    }

    private val back = KButton("Back") {
        mainMenu.isVisible = true
        isVisible = false
    }

    init {
        add(newDeck)
        @Suppress("RedundantLambdaArrow")
        addAll(decks().map { KButton(it.key) { _ ->
            DeckUI(mainMenu, it.value)
            isVisible = false
        } })
        add(back)
        pack()
    }

    companion object {
        val deckFile = File("./decks").apply { mkdirs() }

        fun decks() = deckFile
            .listFiles()
            .map { Deck(it.nameWithoutExtension, it.readText().lines()) }
            .map { it.name to it }
            .toMap()
    }
}
