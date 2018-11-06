package io.github.aedans.ccg.backend

object Action {
    fun play(name: String, card: Card): String = """
        (play ${MRep.string(name)} ${card.mRep()})
    """.trimIndent()
}
