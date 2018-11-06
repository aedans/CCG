package io.github.aedans.server.simple

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import io.github.aedans.ccg.backend.Card
import io.github.aedans.ccg.backend.ConnectionGroup
import java.io.InputStreamReader
import java.io.OutputStreamWriter

data class JsonConnection(val input: JsonReader, val output: JsonWriter) : ConnectionGroup {
    override fun addToHand(name: String, cards: List<Card>) {

    }

    override fun draw(name: String, cards: List<Card>) {

    }

    override fun addMana(name: String, i: Int) {

    }

    override fun gainLife(name: String, i: Int) {

    }

    companion object {
        fun create(connection: Connection) = JsonConnection(
            Gson.newJsonReader(InputStreamReader(connection.input)),
            Gson.newJsonWriter(OutputStreamWriter(connection.output))
        )
    }
}
