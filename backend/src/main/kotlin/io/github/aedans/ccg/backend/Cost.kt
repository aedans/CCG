package io.github.aedans.ccg.backend

interface Cost : MRep {
    data class Mana(val i: Int) : Cost {
        override fun toString() = "($i)"
        override fun asM() = "(mana $i)"
    }

    data class Gem(val gem: io.github.aedans.ccg.backend.Gem) : Cost {
        override fun toString() = "($gem)"
        override fun asM() = "(gem ${MRep.string(gem)})"
    }

    data class Life(val i: Int) : Cost {
        override fun toString() = "($i life)"
        override fun asM() = "(life $i)"
    }

    companion object {
        val env = Env()
            .put("mana", IFunction { args -> Mana(args[0] as Int) })
            .put("gem",
                IFunction { args -> Gem(args[0] as io.github.aedans.ccg.backend.Gem) })
            .put("life", IFunction { args -> Life(args[0] as Int) })
    }
}
