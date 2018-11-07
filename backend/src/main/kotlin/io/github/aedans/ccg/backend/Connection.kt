package io.github.aedans.ccg.backend

data class Connection(val input: ReaderT, val output: WriterT) {
    fun write(output: String) = this.output.write(output)
    fun read() = input.read()
}
