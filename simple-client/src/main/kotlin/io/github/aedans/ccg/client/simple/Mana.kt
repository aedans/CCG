package io.github.aedans.ccg.client.simple

class Mana(available: Boolean) : KLabel(if (available) "M" else "_")
