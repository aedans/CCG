package io.github.aedans.ccg.backend

import java.io.File

data class Card(val name: String) {
    companion object {
        val cardFile = File("./cards")

        fun card(name: String) = Card(name)

        fun cards() = cardFile
            .listFiles()
            .map { card(it.nameWithoutExtension) }
    }
}
