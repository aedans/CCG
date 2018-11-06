package io.github.aedans.ccg.backend

interface Cost : MRep {
    data class Mana(val i: Int) : Cost {
        override fun toString() = "($i)"
        override fun mRep() = "(mana $i)"
    }

    data class Gem(val gem: io.github.aedans.ccg.backend.Gem) : Cost {
        override fun toString() = "($gem)"
        override fun mRep() = "(gem ${MRep.string(gem)})"
    }

    data class Life(val i: Int) : Cost {
        override fun toString() = "($i life)"
        override fun mRep() = "(life $i)"
    }
}
