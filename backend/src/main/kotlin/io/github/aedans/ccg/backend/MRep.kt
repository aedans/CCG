package io.github.aedans.ccg.backend

interface MRep {
    fun mRep(): String

    companion object {
        fun string(mRep: MRep) = mRep.mRep()
        fun string(int: Int) = int.toString()
        fun string(string: String) = "\"${string.replace("\"", "\\\"")}\""
        fun string(list: List<String>) = "(list ${list.joinToString(" ", "", "")})"
        fun string(pair: Pair<String, String>) = "(pair ${pair.first} ${pair.second})"
    }
}
