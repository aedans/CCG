package io.github.aedans.ccg.backend

interface ConnectionGroup {
    fun addToHand(name: String, cards: List<Card>)
    fun draw(name: String, cards: List<Card>)
    fun addMana(name: String, i: Int)
    fun gainLife(name: String, i: Int)

    fun combine(b: ConnectionGroup) = object : ConnectionGroup {
        override fun addToHand(name: String, cards: List<Card>) {
            this@ConnectionGroup.addToHand(name, cards)
            b.addToHand(name, cards)
        }

        override fun draw(name: String, cards: List<Card>) {
            this@ConnectionGroup.draw(name, cards)
            b.draw(name, cards)
        }

        override fun addMana(name: String, i: Int) {
            this@ConnectionGroup.addMana(name, i)
            b.addMana(name, i)
        }

        override fun gainLife(name: String, i: Int) {
            this@ConnectionGroup.gainLife(name, i)
            b.gainLife(name, i)
        }
    }
}
