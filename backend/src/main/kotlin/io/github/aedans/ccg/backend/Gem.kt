package io.github.aedans.ccg.backend

sealed class Gem : MRep {
    object Pearl : Gem() {
        override fun toString() = "Pearl"
        override fun asM() = "pearl"
    }

    object Opal : Gem() {
        override fun toString() = "Opal"
        override fun asM() = "opal"
    }

    object Topaz : Gem() {
        override fun toString() = "Topaz"
        override fun asM() = "topaz"
    }

    object Ruby : Gem() {
        override fun toString() = "Ruby"
        override fun asM() = "ruby"
    }

    object Emerald : Gem() {
        override fun toString() = "Emerald"
        override fun asM() = "emerald"
    }

    object Jade : Gem() {
        override fun toString() = "Jade"
        override fun asM() = "jade"
    }

    object Sapphire : Gem() {
        override fun toString() = "Sapphire"
        override fun asM() = "sapphire"
    }

    object Onyx : Gem() {
        override fun toString() = "Onyx"
        override fun asM() = "onyx"
    }

    companion object {
        val emptyMap = mapOf(
            Pearl to 0, Opal to 0, Topaz to 0, Ruby to 0,
            Emerald to 0, Jade to 0, Sapphire to 0, Onyx to 0
        )

        val env = Env()
            .put("pearl", Gem.Pearl)
            .put("opal", Gem.Opal)
            .put("topaz", Gem.Topaz)
            .put("ruby", Gem.Ruby)
            .put("emerald", Gem.Emerald)
            .put("jade", Gem.Jade)
            .put("sapphire", Gem.Sapphire)
            .put("onyx", Gem.Onyx)
    }
}
