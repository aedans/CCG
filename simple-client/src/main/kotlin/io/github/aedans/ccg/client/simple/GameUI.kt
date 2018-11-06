package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.*
import java.awt.BorderLayout

data class GameUI(val self: String) : KFrame("Game"), ReaderT, WriterT {
    val hand = KHorizontalList()
    val mana = KVerticalList()
    val life = KLabel("0")
    val endTurn = KButton("End Turn") { nextAction.set("null") }

    var nextAction = Notify<String>()

    init {
        add(hand, BorderLayout.SOUTH)
        add(mana, BorderLayout.EAST)
        add(life, BorderLayout.WEST)
        mana.add(endTurn)
    }

    override fun write(string: String) {
        Interpreter.eval(string, env)
    }

    override fun read(): String = run {
        val await = nextAction.await()
        nextAction = Notify()
        await
    }

    fun connection() = Connection(
        ReaderT { this@GameUI.read() },
        WriterT { this@GameUI.write(it) }
    )

    @Suppress("UNCHECKED_CAST")
    val env = Interpreter.env
        .put("add-to-hand", Interpreter.Function { args ->
            val name = args[0] as String
            val cards = args[1] as List<Card>
            if (name == self) {
                cards.forEach { card ->
                    val component = KButton("", CardComponent.cardIcon(card)) { nextAction.set(Action.play(self, card)) }
                    hand.add(component)
                }
                pack()
            }
        })
        .put("draw", Interpreter.Function { args ->
            val name = args[0] as String
            val cards = args[1] as List<Card>
            if (name == self) {
                cards.forEach { card ->
                    val component = KButton("", CardComponent.cardIcon(card)) { nextAction.set(Action.play(self, card)) }
                    hand.add(component)
                }
                pack()
            }
        })
        .put("add-mana", Interpreter.Function { args ->
            val name = args[0] as String
            val i = args[1] as Int
            if (name == self) {
                for (it in 0 until i) {
                    mana.add(Mana())
                    pack()
                }
            }
        })
        .put("gain-life", Interpreter.Function { args ->
            val name = args[0] as String
            val i = args[1] as Int
            if (name == self) {
                life.text = (life.text.toInt() + i).toString()
                pack()
            }
        })
}
