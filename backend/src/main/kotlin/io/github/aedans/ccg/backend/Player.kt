package io.github.aedans.ccg.backend

data class Player(val name: String,
                  val starting: List<Card>,
                  val library: List<Card>,
                  val hand: List<Card>)
