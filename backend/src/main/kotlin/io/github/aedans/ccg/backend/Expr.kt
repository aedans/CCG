package io.github.aedans.ccg.backend

sealed class Expr : MRep {
    override fun asM() = "\'${toString()}"

    data class Identifier(val name: kotlin.String) : Expr() {
        constructor(name: Sequence<Char>) : this(name.joinToString("", "", ""))
        override fun toString() = name
    }

    data class String(val value: kotlin.String) : Expr() {
        constructor(value: Sequence<Char>) : this(value.joinToString("", "", ""))
        override fun toString() = "\"$value\""
    }

    data class Quote(val value: Expr) : Expr() {
        override fun toString() = "\'$value"
    }

    data class List(val exprs: kotlin.collections.List<Expr>) : Expr() {
        constructor(exprs: Sequence<Expr>) : this(exprs.toList())
        override fun toString() = exprs.joinToString(" ", "(", ")")
    }
}
