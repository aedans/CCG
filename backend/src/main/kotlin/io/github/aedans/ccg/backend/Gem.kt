package io.github.aedans.ccg.backend

sealed class Gem : MRep {
    object Pearl : Gem() {
        override fun toString() = "Pearl"
        override fun mRep() = "pearl"
    }

    object Opal : Gem() {
        override fun toString() = "Opal"
        override fun mRep() = "opal"
    }

    object Topaz : Gem() {
        override fun toString() = "Topaz"
        override fun mRep() = "topaz"
    }

    object Ruby : Gem() {
        override fun toString() = "Ruby"
        override fun mRep() = "ruby"
    }

    object Emerald : Gem() {
        override fun toString() = "Emerald"
        override fun mRep() = "emerald"
    }

    object Jade : Gem() {
        override fun toString() = "Jade"
        override fun mRep() = "jade"
    }

    object Sapphire : Gem() {
        override fun toString() = "Sapphire"
        override fun mRep() = "sapphire"
    }

    object Onyx : Gem() {
        override fun toString() = "Onyx"
        override fun mRep() = "onyx"
    }
}
