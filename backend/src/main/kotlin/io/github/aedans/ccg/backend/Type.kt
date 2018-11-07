package io.github.aedans.ccg.backend

sealed class Type : MRep {
    object Artifact : Type() {
        override fun toString() = "Artifact"
        override fun asM() = "artifact"
    }

    object Creature : Type() {
        override fun toString() = "Creature"
        override fun asM() = "creature"
    }

    object Spell : Type() {
        override fun toString() = "Spell"
        override fun asM() = "spell"
    }

    data class Starting(val type: Type) : Type() {
        override fun toString() = "Starting $type"
        override fun asM() = "(starting ${type.asM()})"
    }

    companion object {
        val env = Env()
            .put("artifact", Type.Artifact)
            .put("creature", Type.Creature)
            .put("spell", Type.Spell)
            .put("starting", IFunction { args -> Starting(args[0] as Type) })
    }
}
