package io.github.aedans.ccg.backend

import java.io.File

data class Card(val name: String,
                val type: String,
                val text: String,
                val cost: String,
                val stats: Pair<Int, Int>?) {
    companion object {
        val cardFile = File("./cards")

        fun card(name: String) = run {
            val script = File(cardFile, "$name.m").readLines().joinToString("", "", "")
            val result = Parser.parser(script.asSequence())
            val exprs = when (result) {
                is Parser.Result.Failure -> throw Exception("Could not parse $name.m")
                is Parser.Result.Success -> if (result.rest.any())
                    throw Exception("Could not parse $name.m at char '${result.rest.first()}'")
                else
                    result.value
            }
            val localEnv = Interpreter.run(exprs.toList(), env)
            @Suppress("UNCHECKED_CAST")
            Card(
                localEnv["name"] as String,
                localEnv["type"] as String,
                localEnv["text"] as String,
                localEnv["cost"] as String,
                localEnv["stats"] as Pair<Int, Int>?
            )
        }

        fun cards() = cardFile
            .listFiles()
            .map { card(it.nameWithoutExtension) }

        val env = Interpreter.Env(emptyMap())
            .put("pair", Interpreter.Function { args -> args[0] to args[1] })
    }
}
