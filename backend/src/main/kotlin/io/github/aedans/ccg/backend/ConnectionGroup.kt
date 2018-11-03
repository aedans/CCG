package io.github.aedans.ccg.backend

interface ConnectionGroup {
    fun addToHand(name: String, cards: List<Card>)
    fun draw(name: String, i: Int)

    fun combine(b: ConnectionGroup) = object : ConnectionGroup {
        override fun addToHand(name: String, cards: List<Card>) {
            this@ConnectionGroup.addToHand(name, cards)
            b.addToHand(name, cards)
        }

        override fun draw(name: String, i: Int) {
            this@ConnectionGroup.draw(name, i)
            b.draw(name, i)
        }
    }
}
