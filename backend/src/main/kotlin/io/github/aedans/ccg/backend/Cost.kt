package io.github.aedans.ccg.backend

import io.github.aedans.ccg.backend.MRep.Companion.string

interface Cost : MRep {
    fun canBePaid(player: Player): Boolean
    fun pay(player: Player): Action

    data class Mana(val i: Int) : Cost {
        override fun toString() = "($i)"
        override fun asM() = "(mana $i)"
        override fun canBePaid(player: Player) = player.currentMana >= i
        override fun pay(player: Player) = Action.AddCurrentMana(player.name, -i)
    }

    data class Gem(val gem: io.github.aedans.ccg.backend.Gem, val i: Int) : Cost {
        override fun toString() = "($gem)"
        override fun asM() = "(gem ${string(gem)} ${string(i)})"
        override fun canBePaid(player: Player) = player.gems[gem]!! >= i
        override fun pay(player: Player) = Action.DoNothing
    }

    data class Life(val i: Int) : Cost {
        override fun toString() = "($i life)"
        override fun asM() = "(life $i)"
        override fun canBePaid(player: Player) = player.life > i
        override fun pay(player: Player) = Action.AddLife(player.name, -i)
    }

    data class Tap(val card: Card) : Cost {
        override fun toString() = "(tap ${card.name})"
        override fun asM() = "(tap ${string(card)})"
        override fun canBePaid(player: Player) = player.field.contains(card.copy(tapped = false))
        override fun pay(player: Player) = Action.Tap(player.name, card)
    }

    companion object {
        val env = Env()
            .put("mana", IFunction { args -> Mana(args[0] as Int) })
            .put("gem", IFunction { args -> Gem(args[0] as io.github.aedans.ccg.backend.Gem, args[1] as Int) })
            .put("life", IFunction { args -> Life(args[0] as Int) })
            .put("tap", IFunction { args -> Tap(args[0] as Card) })
    }
}
