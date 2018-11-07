package io.github.aedans.ccg.backend

import io.github.aedans.ccg.backend.MRep.Companion.string

data class Player(
    val name: String,
    val starting: List<Card>,
    val library: List<Card>,
    val hand: List<Card> = emptyList(),
    val field: List<Card> = emptyList(),
    val mana: Int = 0,
    val life: Int = 0
) : MRep {
    override fun asM() = "(player" +
            "${string(name)} " +
            "${string(starting.map { string(it) })} " +
            "${string(library.map { string(it) })} " +
            "${string(hand.map { string(it) })} " +
            "${string(field.map { string(it) })} " +
            "${string(mana)} " +
            "${string(life)} " +
            ")"

    companion object {
        val env = Env().put("player", IFunction { args ->
            @Suppress("UNCHECKED_CAST")
            Player(
                args[0] as String,
                args[1] as List<Card>,
                args[2] as List<Card>,
                args[3] as List<Card>,
                args[4] as List<Card>,
                args[5] as Int,
                args[6] as Int
            )
        })
    }
}
