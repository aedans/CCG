package io.github.aedans.ccg.client.simple

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.cancelAndJoin

class WaitingFor(deferred: Deferred<Any>) : KMenuFrame("Waiting for") {
    val waiting = KLabel("Waiting")
    val cancel = KButton("Cancel") { deferred.cancelAndJoin() }

    init {
        addAll(waiting, cancel)
        pack()
    }
}
