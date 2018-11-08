package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Gem

class GemUI(gem: Gem, i: Int) : KLabel("$i $gem")
