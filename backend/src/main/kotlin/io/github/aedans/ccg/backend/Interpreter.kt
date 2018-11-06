package io.github.aedans.ccg.backend

object Interpreter {
    data class Env(val globals: Map<String, Any?>) {
        operator fun get(name: String) = globals[name]
        fun put(name: String, any: Any?) = copy(globals = globals + (name to any))
    }

    interface Function {
        operator fun invoke(args: List<Any?>): Any?

        companion object {
            operator fun invoke(fn: (List<Any?>) -> Any?) = object : Function {
                override fun invoke(args: List<Any?>) = fn(args)
            }
        }
    }

    fun run(string: String, env: Env = Interpreter.env) =
        Interpreter.run(Parser.parse(string, Parser.parser).toList(), env)

    fun eval(string: String, env: Env = Interpreter.env) =
        Interpreter.eval(Parser.parse(string, Parser.exprParser), env)

    inline fun <reified T> value(string: String, env: Env = Interpreter.env) = eval(string, env).first as T

    fun run(exprs: List<Expr>, env: Env) = exprs.fold(env) { a: Env, b: Expr -> eval(b, a).second }

    private fun eval(expr: Expr, env: Env): Pair<Any?, Env> = when (expr) {
        Expr.Identifier("null") -> null to env
        is Expr.String -> expr.value to env
        is Expr.Identifier -> (env[expr.name] ?: expr.name.toIntOrNull() ?: throw Exception("Could not find ${expr.name}")) to env
        is Expr.List -> when (expr.exprs[0]) {
            Expr.Identifier("def") -> {
                val name = (expr.exprs[1] as Expr.Identifier).name
                val (value, _) = eval(expr.exprs[2], env)
                Unit to env.copy(globals = env.globals + (name to value))
            }
            Expr.Identifier("list") -> expr.exprs.drop(1).map { eval(it, env).first } to env
            else -> (eval(expr.exprs.first(), env).first as Function)(expr.exprs.drop(1).map { eval(it, env).first }) to env
        }
    }

    @Suppress("UNCHECKED_CAST")
    val env = Interpreter.Env(emptyMap())
        .put("null", null)
        .put("artifact", Type.Artifact)
        .put("creature", Type.Creature)
        .put("spell", Type.Spell)
        .put("starting", Function { args -> Type.Starting(args[0] as Type) })
        .put("pair", Function { args -> args[0] to args[1] })
        .put("mana", Function { args -> Cost.Mana(args[0] as Int) })
        .put("gem", Function { args -> Cost.Gem(args[0] as Gem) })
        .put("life", Function { args -> Cost.Life(args[0] as Int) })
        .put("pearl", Gem.Pearl)
        .put("opal", Gem.Opal)
        .put("topaz", Gem.Topaz)
        .put("ruby", Gem.Ruby)
        .put("emerald", Gem.Emerald)
        .put("jade", Gem.Jade)
        .put("sapphire", Gem.Sapphire)
        .put("onyx", Gem.Onyx)
        .put("card", Function { args ->
            Card(
                args[0] as String,
                args[1] as String,
                args[2] as Type,
                args[3] as List<Cost>,
                args[4] as Pair<Int, Int>?
            )
        })
        .put("player", Function { args ->
            Player(
                args[0] as String,
                args[1] as List<Card>,
                args[2] as List<Card>,
                args[3] as List<Card>,
                args[4] as List<Card>,
                args[5] as Int,
                args[6] as Int
            )
        })
}
