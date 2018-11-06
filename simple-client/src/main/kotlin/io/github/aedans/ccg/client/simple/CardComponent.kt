package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Card
import java.awt.Color
import java.awt.Dimension
import java.awt.image.BufferedImage

class CardComponent(card: Card) : KImage(cardImage(card)) {
    init {
        preferredSize = Dimension(480 / 2, 720 / 2)
    }

    companion object {
        fun cardImage(card: Card): BufferedImage {
            val width = 480 / 3
            val height = 720 / 3
            val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            val graphics = bufferedImage.graphics

            val half = height / 2

            val fontHeight = graphics.fontMetrics.height

            graphics.color = Color.WHITE
            graphics.fillRect(0, 0, width, height)

            graphics.color = Color.BLACK
            graphics.drawString(card.name, 5, fontHeight)

            val cost = card.cost
            val costWidth = graphics.fontMetrics.stringWidth(cost) + 5
            val costOffset = if (graphics.fontMetrics.stringWidth(card.name) + costWidth > width) fontHeight else 0
            graphics.drawString(cost, width - costWidth, fontHeight + costOffset)

            graphics.drawString(card.type, 5, half)

            val words = card.text.split(' ')
            var x = 5
            var y = half + fontHeight + 10
            for (word in words) {
                val stringWidth = graphics.fontMetrics.stringWidth("$word ")
                if (x + stringWidth >= width) {
                    x = 5
                    y += fontHeight
                }
                graphics.drawString("$word ", x, y)
                x += stringWidth
            }

            val stats = card.stats
            if (stats != null) {
                val powerToughness = stats.first.toString() + "/" + stats.first.toString()
                val powerToughnessWidth = graphics.fontMetrics.stringWidth(powerToughness) + 5
                graphics.drawString(powerToughness, width - powerToughnessWidth, height - 5)
            }

            return bufferedImage
        }
    }
}
