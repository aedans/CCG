package io.github.aedans.ccg.client.simple

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.cancelAndJoin

class WaitingFor(deferred: Deferred<Any>) : KMenuFrame("Waiting for", Waiting, Cancel(deferred)) {
    object Waiting : KLabel("Waiting")
    class Cancel(private val deferred: Deferred<Any>) : KButton("Cancel", { deferred.cancelAndJoin() })
}
