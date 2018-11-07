package io.github.aedans.ccg.backend

interface IFunction {
    operator fun invoke(args: List<Any?>): Any?

    companion object {
        operator fun invoke(fn: (List<Any?>) -> Any?) = object : IFunction {
            override fun invoke(args: List<Any?>) = fn(args)
        }
    }
}