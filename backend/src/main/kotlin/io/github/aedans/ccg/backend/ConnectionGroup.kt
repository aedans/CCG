package io.github.aedans.ccg.backend

interface ConnectionGroup {
    fun addToHand(name: String, cards: List<Card>)
    fun draw(name: String, cards: List<Card>)

    fun combine(b: ConnectionGroup) = object : ConnectionGroup {
        override fun addToHand(name: String, cards: List<Card>) {
            this@ConnectionGroup.addToHand(name, cards)
            b.addToHand(name, cards)
        }

        override fun draw(name: String, cards: List<Card>) {
            this@ConnectionGroup.draw(name, cards)
            b.draw(name, cards)
        }
    }
}
