package io.github.aedans.ccg.client.simple

open class KExceptionPopup(it: Throwable) : KPopup("Error", it.localizedMessage)
