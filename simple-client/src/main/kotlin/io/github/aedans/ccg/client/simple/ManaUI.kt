package io.github.aedans.ccg.client.simple

class ManaUI(available: Boolean) : KLabel(if (available) "M" else "_")
