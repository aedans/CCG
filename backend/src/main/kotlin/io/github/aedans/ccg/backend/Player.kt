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
    override fun mRep() = "(player" +
            "${string(name)} " +
            "${string(starting.map { string(it) })} " +
            "${string(library.map { string(it) })} " +
            "${string(hand.map { string(it) })} " +
            "${string(field.map { string(it) })} " +
            "${string(mana)} " +
            "${string(life)} " +
            ")"
}
