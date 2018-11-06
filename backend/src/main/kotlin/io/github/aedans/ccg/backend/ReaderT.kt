package io.github.aedans.ccg.backend

import java.io.InputStream

interface ReaderT {
    fun read(): String?

    fun pipe(writerT: WriterT) = run {
        tailrec fun write() {
            val read = read()
            if (read != null) {
                writerT.write(read)
                write()
            }
        }
        write()
    }

    companion object {
        operator fun invoke(fn: () -> String?) = object : ReaderT {
            override fun read() = fn()
        }

        fun create(input: InputStream) = run {
            val reader = input.bufferedReader()
            ReaderT { reader.readLine() }
        }
    }
}