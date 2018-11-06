package io.github.aedans.ccg.backend

sealed class Type : MRep {
    object Artifact : Type() {
        override fun toString() = "Artifact"
        override fun mRep() = "artifact"
    }

    object Creature : Type() {
        override fun toString() = "Creature"
        override fun mRep() = "creature"
    }

    object Spell : Type() {
        override fun toString() = "Spell"
        override fun mRep() = "spell"
    }

    data class Starting(val type: Type) : Type() {
        override fun toString() = "Starting $type"
        override fun mRep() = "(starting ${type.mRep()})"
    }
}
