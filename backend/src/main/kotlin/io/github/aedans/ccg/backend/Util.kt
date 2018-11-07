package io.github.aedans.ccg.backend

fun <K, V> Map<K, V>.update(key: K, fn: (V) -> V): Map<K, V> = run {
    val new = this[key]
    new?.let { this + (key to fn(it)) } ?: this
}

infix fun <T> List<T>.plusAll(list: List<T>) = list.fold(this) { a, b -> a + b }
infix fun <T> List<T>.minusAll(list: List<T>) = list.fold(this) { a, b -> a - b }
