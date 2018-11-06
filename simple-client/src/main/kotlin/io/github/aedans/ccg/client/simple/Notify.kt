package io.github.aedans.ccg.client.simple

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
class Notify<T : Any> private constructor(var t: T?) : java.lang.Object() {
    constructor() : this(null)

    @Synchronized
    fun set(t: T) {
        this.t = t
        notify()
    }

    @Synchronized
    fun await(): T {
        wait()
        return t!!
    }
}
