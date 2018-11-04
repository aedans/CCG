package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Card
import java.awt.BorderLayout
import java.awt.GridLayout
import java.io.File
import javax.swing.ImageIcon
import javax.swing.JScrollPane
import javax.swing.ScrollPaneConstants

class DeckUI(mainMenu: MainMenu, deck: Deck) : KFrame("Deck Builder") {
    private val cards: MutableList<String> = mutableListOf()

    val name = KTextField(deck.name)

    val save = KButton("Save") {
        File(Deck.deckFile, name.text)
            .writeText((listOf(starter1.text, starter2.text, starter3.text) + cards)
            .joinToString("\n", "", ""))
    }

    val delete = KButton("Delete") {
        File(Deck.deckFile, name.text).delete()
    }

    val size = KLabel("")

    val starters = KLabel("Starters: ")
    val starter1 = KTextField(deck.starter1)
    val starter2 = KTextField(deck.starter2)
    val starter3 = KTextField(deck.starter3)

    val exit = KButton("Exit") {
        isVisible = false
        DecksMenu(mainMenu)
    }

    val menu = KHorizontalList().apply {
        addAll(this@DeckUI.name, save, delete, this@DeckUI.size, exit, starters, starter1, starter2, starter3)
    }

    private val cardList = KPanel().apply {
        layout = GridLayout(0, 2)
    }

    private val deckCards = JScrollPane(cardList).apply {
        verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
        horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
    }

    private val allCards = JScrollPane(KPanel().apply {
        layout = GridLayout(4, 0)
        addAll(Card.cards().map { card -> KButton("", ImageIcon(CardComponent.cardImage(card))) { add(card.name) } })
    }).apply {
        horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
    }

    private fun refresh() {
        cardList.removeAll()
        cards.sort()
        cards.forEach { name ->
            cardList.add(KButton(name) {
                remove(name)
            }).apply {
                font = font.deriveFont(font.size / 2f)
            }
        }
        size.text = "Size: ${cards.size}"
        pack()
    }

    private fun add(name: String) {
        cards += name
        refresh()
    }

    private fun remove(name: String) {
        cards -= name
        refresh()
    }

    init {
        layout = BorderLayout()

        deck.cards.forEach { add(it) }

        add(menu, BorderLayout.NORTH)
        add(deckCards, BorderLayout.EAST)
        add(allCards, BorderLayout.CENTER)
        refresh()
        pack()
    }
}
