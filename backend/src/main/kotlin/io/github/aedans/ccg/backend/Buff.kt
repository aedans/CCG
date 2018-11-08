package io.github.aedans.ccg.backend

interface Buff : MRep {
    data class Damaged(val i: Int) : Buff {
        override fun toString() = "Damaged ($i)"
        override fun asM() = "(damaged $i)"
    }

    object Tapped : Buff {
        override fun toString() = "Tapped"
        override fun asM() = "tapped"
    }

    companion object {
        val env = Env()
            .put("damaged", IFunction { args -> Damaged(args[0] as Int) })
            .put("tapped", Tapped)
    }
}
