package io.github.aedans.ccg.backend

object Interpreter {
    data class Env(val globals: Map<String, Any>) {
        operator fun get(name: String) = globals[name]
        fun put(name: String, any: Any) = copy(globals = globals + (name to any))
    }

    interface Function {
        operator fun invoke(args: List<Any>): Any

        companion object {
            operator fun invoke(fn: (List<Any>) -> Any) = object : Function {
                override fun invoke(args: List<Any>) = fn(args)
            }
        }
    }

    fun run(exprs: List<Expr>, env: Env) = exprs.fold(env) { a: Env, b: Expr -> run(b, a).second }

    fun run(expr: Expr, env: Env): Pair<Any, Env> = when (expr) {
        is Expr.String -> expr.value to env
        is Expr.Identifier -> (env[expr.name] ?: expr.name.toIntOrNull() ?: throw Exception("Could not find ${expr.name}")) to env
        is Expr.List -> when (expr.exprs[0]) {
            Expr.Identifier("def") -> {
                val name = (expr.exprs[1] as Expr.Identifier).name
                val (value, _) = run(expr.exprs[2], env)
                Unit to env.copy(globals = env.globals + (name to value))
            }
            else -> (run(expr.exprs.first(), env).first as Function)(expr.exprs.drop(1).map { run(it, env).first }) to env
        }
    }
}
