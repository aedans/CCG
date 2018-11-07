package io.github.aedans.ccg.backend

object Interpreter {
    fun run(string: String, env: Env = Interpreter.env) =
        Interpreter.run(Parser.parse(string, Parser.parser).toList(), env)

    fun eval(string: String, env: Env = Interpreter.env) =
        Interpreter.eval(Parser.parse(string, Parser.exprParser), env)

    inline fun <reified T> value(string: String, env: Env = Interpreter.env) = eval(string, env).first as T

    inline fun <reified T> value(expr: Expr, env: Env = Interpreter.env) = eval(expr, env).first as T

    fun run(exprs: List<Expr>, env: Env) = exprs.fold(env) { a: Env, b: Expr -> eval(b, a).second }

    fun eval(expr: Expr, env: Env): Pair<Any?, Env> = when (expr) {
        Expr.Identifier("null") -> null to env
        is Expr.String -> expr.value to env
        is Expr.Quote -> expr.value to env
        is Expr.Identifier -> (env[expr.name] ?: expr.name.toIntOrNull() ?: throw Exception("Could not find ${expr.name}")) to env
        is Expr.List -> when (expr.exprs[0]) {
            Expr.Identifier("def") -> {
                val name = (expr.exprs[1] as Expr.Identifier).name
                val (value, _) = eval(expr.exprs[2], env)
                if (value == null)
                    Unit to env
                else
                    Unit to env.copy(globals = env.globals + (name to value))
            }
            Expr.Identifier("list") -> expr.exprs.drop(1).map { eval(it, env).first } to env
            else -> (eval(expr.exprs.first(), env).first as IFunction)(expr.exprs.drop(1).map { eval(it, env).first }) to env
        }
    }

    @Suppress("UNCHECKED_CAST")
    val env = Env()
        .put(Type.env)
        .put(Cost.env)
        .put(Gem.env)
        .put(Card.env)
        .put(Player.env)
        .put("pair", IFunction { args -> args[0] to args[1] })
        .put("map", IFunction { args -> (args[0] as List<Pair<Any, Any>>).toMap() })
}
