package io.github.aedans.ccg.backend

data class Player(val name: String,
                  val starting: List<Card>,
                  val library: List<Card>,
                  val hand: List<Card> = emptyList(),
                  val field: List<Card> = emptyList(),
                  val mana: Int = 0,
                  val life: Int = 0)
