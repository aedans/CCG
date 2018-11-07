package io.github.aedans.ccg.backend

import io.github.aedans.ccg.backend.MRep.Companion.string
import org.intellij.lang.annotations.Language
import java.io.File

data class Card(
    val name: String,
    val text: String,
    val type: Type,
    val cost: List<Cost>,
    val stats: Pair<Int, Int>?,
    val tapped: Boolean,
    val cast: Expr,
    val enterField: Expr,
    val leaveField: Expr
) : MRep {
    override fun asM(): String {
        return "(card " +
                "${string(name)} " +
                "${string(text)} " +
                "${string(type)} " +
                "${string(cost.map { string(it) })} " +
                "${stats?.let { string(string(it.first) to string(it.second)) }} " +
                "${string(tapped)} " +
                "${string(cast)} " +
                "${string(enterField)} " +
                "${string(leaveField)})"
    }

    companion object {
        @Language("m")
        val castPermanent = "(add-cards-to-field caster (list this))"

        val env = Env()
            .put("card", IFunction { args ->
                @Suppress("UNCHECKED_CAST")
                Card(
                    args[0] as String,
                    args[1] as String,
                    args[2] as Type,
                    args[3] as List<Cost>,
                    args[4] as Pair<Int, Int>?,
                    args[5] as Boolean,
                    args[6] as Expr,
                    args[7] as Expr,
                    args[8] as Expr
                )
            })
            .put("cast-permanent", Parser.parse(castPermanent, Parser.exprParser))

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
                localEnv["stats"] as Pair<Int, Int>?,
                (localEnv["tapped"] as Boolean?) ?: false,
                (localEnv["cast"] as Expr?) ?: Expr.Identifier("do-nothing"),
                (localEnv["enters-field"] as Expr?) ?: Expr.Identifier("do-nothing"),
                (localEnv["leaves-field"] as Expr?) ?: Expr.Identifier("do-nothing")
            )
        }

        fun cards() = cardFile
            .listFiles()
            .map { card(it.nameWithoutExtension) }
    }
}
