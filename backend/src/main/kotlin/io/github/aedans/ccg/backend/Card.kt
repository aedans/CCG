package io.github.aedans.ccg.backend

import io.github.aedans.ccg.backend.MRep.Companion.string
import java.io.File

data class Card(val name: String,
                val text: String,
                val type: Type,
                val cost: List<Cost>,
                val stats: Pair<Int, Int>?) : MRep {
    override fun mRep(): String {
        return "(card " +
                "${string(name)} " +
                "${string(text)} " +
                "${string(type)} " +
                "${string(cost.map { string(it) })} " +
                "${stats?.let { string(string(it.first) to string(it.second)) }})"
    }

    companion object {
        val cardFile = File("./cards")

        fun card(name: String) = run {
            val script = File(cardFile, "$name.m").readLines().joinToString("", "", "")
            val localEnv = try {
                Interpreter.run(script)
            } catch (e: Exception) {
                throw Exception("Error loading card $name", e)
            }
            @Suppress("UNCHECKED_CAST")
            Card(
                localEnv["name"] as String,
                localEnv["text"] as String,
                localEnv["type"] as Type,
                localEnv["cost"] as List<Cost>,
                localEnv["stats"] as Pair<Int, Int>?
            )
        }

        fun cards() = cardFile
            .listFiles()
            .map { card(it.nameWithoutExtension) }
    }
}
