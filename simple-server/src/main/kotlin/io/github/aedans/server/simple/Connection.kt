package io.github.aedans.server.simple

import java.io.InputStream
import java.io.OutputStream

data class Connection(val input: InputStream, val output: OutputStream)
