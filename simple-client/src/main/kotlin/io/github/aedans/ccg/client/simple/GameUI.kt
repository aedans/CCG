package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.*
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JScrollPane
import javax.swing.ScrollPaneConstants

data class GameUI(val self: String) : KFrame("Game"), ReaderT, WriterT {
    val hand = KHorizontalList()
    val field = KHorizontalList()
    val mana = KVerticalList()
    val life = KLabel("0")
    val endTurn = KButton("End Turn") { nextAction.set(Action.EndTurn) }

    val handCards = mutableListOf<Card>()
    val fieldCards = mutableListOf<Card>()
    var maxMana = 0
    var currentMana = 0
    val gems = mutableMapOf<Gem, Int>().apply {
        putAll(Gem.emptyMap)
    }

    var nextAction = Notify<Action>()

    init {
        add(JScrollPane(hand).apply {
            preferredSize = Dimension(CardComponent.width * 10, CardComponent.height + 20)
            horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER
        }, BorderLayout.SOUTH)

        add(JScrollPane(field).apply {
            preferredSize = Dimension(CardComponent.width * 10, CardComponent.height + 20)
            horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER
        }, BorderLayout.CENTER)

        add(mana, BorderLayout.EAST)
        add(life, BorderLayout.WEST)
    }

    private fun updateHand() {
        hand.removeAll()
        handCards.sortBy { it.name }
        handCards.forEach { card ->
            val component = KButton("", CardComponent.cardIcon(card)) { nextAction.set(Action.Cast(self, card)) }
            hand.add(component)
        }
        pack()
        repaint()
    }

    private fun updateField() {
        field.removeAll()
        fieldCards.sortBy { it.name }
        fieldCards.forEach { card ->
            val component = KButton("", CardComponent.cardIcon(card)) { }
            field.add(component)
        }
        pack()
        repaint()
    }

    private fun updateMana() {
        mana.removeAll()
        mana.add(endTurn)
        for (i in 0 until maxMana) {
            mana.add(ManaUI(i < currentMana))
        }
        for ((gem, number) in gems) {
            if (number != 0)
                mana.add(GemUI(gem, number))
        }
        pack()
        repaint()
    }

    override fun write(string: String) {
        Interpreter.eval(string, env)
    }

    override fun read(): String = run {
        val await = nextAction.await()
        nextAction = Notify()
        await.asM()
    }

    fun connection() = Connection(
        ReaderT { this@GameUI.read() },
        WriterT { this@GameUI.write(it) }
    )

    @Suppress("UNCHECKED_CAST")
    val env = Interpreter.env
        .put("add-cards-to-hand", IFunction { args ->
            val name = args[0] as String
            val cards = args[1] as List<Card>
            if (name == self) {
                handCards.addAll(cards)
                updateHand()
            }
        })
        .put("remove-cards-from-hand", IFunction { args ->
            val name = args[0] as String
            val cards = args[1] as List<Card>
            if (name == self) {
                cards.forEach { handCards.remove(it) }
                updateHand()
            }
        })
        .put("add-cards-to-field", IFunction { args ->
            val name = args[0] as String
            val cards = args[1] as List<Card>
            if (name == self) {
                fieldCards.addAll(cards)
                updateField()
            }
        })
        .put("remove-cards-from-field", IFunction { args ->
            val name = args[0] as String
            val cards = args[1] as List<Card>
            if (name == self) {
                fieldCards.removeAll(cards)
                updateField()
            }
        })
        .put("add-cards-to-library", IFunction { args ->

        })
        .put("remove-cards-from-library", IFunction { args ->

        })
        .put("add-max-mana", IFunction { args ->
            val name = args[0] as String
            val i = args[1] as Int
            if (name == self) {
                maxMana += i
                updateMana()
            }
        })
        .put("add-current-mana", IFunction { args ->
            val name = args[0] as String
            val i = args[1] as Int
            if (name == self) {
                currentMana += i
                updateMana()
            }
        })
        .put("add-life", IFunction { args ->
            val name = args[0] as String
            val i = args[1] as Int
            if (name == self) {
                life.text = (life.text.toInt() + i).toString()
                pack()
            }
        })
        .put("tap", IFunction { args ->
            val name = args[0] as String
            val card = args[1] as Card
            if (name == self) {
                fieldCards.remove(card)
                fieldCards.add(card.copy(tapped = true))
                updateField()
            }
        })
        .put("untap", IFunction { args ->
            val name = args[0] as String
            val card = args[1] as Card
            if (name == self) {
                fieldCards.remove(card)
                fieldCards.add(card.copy(tapped = false))
                updateField()
            }
        })
        .put("add-gem", IFunction { args ->
            val name = args[0] as String
            val gem = args[1] as Gem
            val i = args[2] as Int
            if (name == self) {
                gems[gem] = gems[gem]!! + i
                updateMana()
            }
        })
        .put("end-turn", IFunction { args ->

        })
        .put("do-nothing", IFunction { args ->

        })
}
