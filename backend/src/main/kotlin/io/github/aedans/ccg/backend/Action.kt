package io.github.aedans.ccg.backend

import io.github.aedans.ccg.backend.MRep.Companion.string

interface Action : MRep {
    fun run(players: Map<String, Player>): Map<String, Player>

    fun flatten(players: Map<String, Player>): List<Action> = when {
        this is Composite -> base(players).flatMap { it.flatten(players) }
        else -> listOf(this)
    }

    interface Composite : Action {
        override fun run(players: Map<String, Player>) =
            base(players).fold(players) { p, action -> action.run(p) }

        fun base(players: Map<String, Player>): List<Action>
    }

    data class Cast(val name: String, val card: Card) : Composite {
        override fun base(players: Map<String, Player>) = run {
            val env = Action.env
                .put("caster", name)
                .put("this", card)
            listOf(
                RemoveCardsFromHand(name, listOf(card)),
                Interpreter.value<Action>(card.cast, env)
            )
        }

        override fun asM() = "(cast ${string(name)} ${card.asM()})"
    }

    data class Draw(val name: String, val i: Int) : Composite {
        override fun base(players: Map<String, Player>) = players[name]!!.run {
            val number = if (library.size <= i) library.size else i
            val cards = library.take(number)
            listOf(
                RemoveCardsFromLibrary(name, cards),
                AddCardsToHand(name, cards)
            )
        }

        override fun asM() = "(draw ${string(name)} $i"
    }

    data class AddCardsToHand(val name: String, val cards: List<Card>) : Action {
        override fun run(players: Map<String, Player>) = players.update(name) { it.copy(hand = it.hand plusAll cards) }
        override fun asM() = "(add-cards-to-hand ${string(name)} ${string(cards.map { string(it) })})"
    }

    data class RemoveCardsFromHand(val name: String, val cards: List<Card>) : Action {
        override fun run(players: Map<String, Player>) = players.update(name) { it.copy(hand = it.hand minusAll cards) }
        override fun asM() = "(remove-cards-from-hand ${string(name)} ${string(cards.map { string(it) })})"
    }

    data class AddCardsToLibrary(val name: String, val cards: List<Card>) : Action {
        override fun run(players: Map<String, Player>) = players.update(name) { it.copy(library = it.library plusAll cards) }
        override fun asM() = "(add-cards-to-library ${string(name)} ${string(cards.map { string(it) })})"
    }

    data class RemoveCardsFromLibrary(val name: String, val cards: List<Card>) : Action {
        override fun run(players: Map<String, Player>) = players.update(name) { it.copy(library = it.library minusAll cards) }
        override fun asM() = "(remove-cards-from-library ${string(name)} ${string(cards.map { string(it) })})"
    }

    data class AddCardsToField(val name: String, val cards: List<Card>) : Action {
        override fun run(players: Map<String, Player>) = players.update(name) { it.copy(field = it.field plusAll cards) }
        override fun asM() = "(add-cards-to-field ${string(name)} ${string(cards.map { string(it) })})"
    }

    data class RemoveCardsFromField(val name: String, val cards: List<Card>) : Action {
        override fun run(players: Map<String, Player>) = players.update(name) { it.copy(field = it.field minusAll cards) }
        override fun asM() = "(remove-cards-from-field ${string(name)} ${string(cards.map { string(it) })})"
    }

    data class AddMana(val name: String, val i: Int) : Action {
        override fun run(players: Map<String, Player>) = players.update(name) { it.copy(mana = it.mana + i) }
        override fun asM() = "(add-mana ${string(name)} ${string(i)})"
    }

    data class AddLife(val name: String, val i: Int) : Action {
        override fun run(players: Map<String, Player>) = players.update(name) { it.copy(life = it.life + i) }
        override fun asM() = "(add-life ${string(name)} ${string(i)})"
    }

    object DoNothing : Action {
        override fun run(players: Map<String, Player>) = players
        override fun asM() = "do-nothing"
    }

    object EndTurn : Action {
        override fun run(players: Map<String, Player>) = players
        override fun asM() = "end-turn"
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val env = Interpreter.env
            .put("cast", IFunction { args -> Cast(args[0] as String, args[1] as Card) })
            .put("draw", IFunction { args -> Draw(args[0] as String, args[1] as Int) })
            .put("add-cards-to-hand", IFunction { args -> AddCardsToHand(args[0] as String, args[1] as List<Card>) })
            .put("remove-cards-from-hand", IFunction { args -> RemoveCardsFromHand(args[0] as String, args[1] as List<Card>) })
            .put("add-cards-to-library", IFunction { args -> AddCardsToLibrary(args[0] as String, args[1] as List<Card>) })
            .put("remove-cards-from-library", IFunction { args -> RemoveCardsFromLibrary(args[0] as String, args[1] as List<Card>) })
            .put("add-cards-to-field", IFunction { args -> AddCardsToField(args[0] as String, args[1] as List<Card>) })
            .put("remove-cards-from-field", IFunction { args -> RemoveCardsFromField(args[0] as String, args[1] as List<Card>) })
            .put("add-life", IFunction { args -> AddLife(args[0] as String, args[1] as Int) })
            .put("add-mana", IFunction { args -> AddMana(args[0] as String, args[1] as Int) })
            .put("do-nothing", DoNothing)
            .put("end-turn", EndTurn)
    }
}
