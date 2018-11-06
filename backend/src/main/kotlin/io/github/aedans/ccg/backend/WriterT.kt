package io.github.aedans.ccg.backend

import java.io.OutputStream

interface WriterT {
    fun write(string: String)

    companion object {
        operator fun invoke(fn: (String) -> Unit) = object : WriterT {
            override fun write(string: String) = fn(string)
        }

        fun create(output: OutputStream) = run {
            val writer = output.bufferedWriter()
            WriterT {
                writer.write(it)
                writer.newLine()
                writer.flush()
            }
        }
    }
}