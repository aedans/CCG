package io.github.aedans.ccg.backend

data class Env(val globals: Map<String, Any> = emptyMap()) {
    operator fun get(name: String) = globals[name]
    fun put(name: String, any: Any) = copy(globals = globals + (name to any))
    fun put(env: Env) = copy(globals = globals + env.globals)
}