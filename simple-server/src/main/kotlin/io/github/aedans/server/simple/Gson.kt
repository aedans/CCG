package io.github.aedans.server.simple

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

val Gson = com.google.gson.GsonBuilder().apply {
}.create()

inline fun <reified T> JsonReader.read() = Gson.fromJson<T>(this, T::class.java)!!

inline fun <reified T> JsonWriter.write(t: T) = run {
    Gson.toJson(t, T::class.java, this)
    flush()
}
