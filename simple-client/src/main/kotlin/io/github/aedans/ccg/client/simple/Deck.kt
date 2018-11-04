package io.github.aedans.ccg.client.simple

import java.io.File

data class Deck(val name: String, val starter1: String, val starter2: String, val starter3: String, val cards: List<String>) {
    companion object {
        val deckFile = File("./decks").apply { mkdirs() }

        fun deck(name: String) = File(deckFile, name)
            .let {
                val lines = it.readText().lines()
                Deck(it.nameWithoutExtension, lines[0], lines[1], lines[2], lines.drop(3))
            }

        fun decks() = deckFile
            .listFiles()
            .map { deck(it.nameWithoutExtension) }
            .map { it.name to it }
            .toMap()
    }
}
