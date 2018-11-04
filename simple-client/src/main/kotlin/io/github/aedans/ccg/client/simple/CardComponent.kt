package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Card
import java.awt.Color
import java.awt.Dimension
import java.awt.image.BufferedImage

class CardComponent(card: Card) : KImage(cardImage(card)) {
    init {
        preferredSize = Dimension(480 / 4, 720 / 4)
    }

    companion object {
        fun cardImage(card: Card): BufferedImage {
            val width = 480 / 4
            val height = 720 / 4
            val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            val graphics = bufferedImage.graphics
            graphics.color = Color.WHITE
            graphics.fillRect(0, 0, width, height)
            graphics.color = Color.BLACK
            graphics.drawString(card.name, 5, 15)
            return bufferedImage
        }
    }
}
