package io.github.aedans.ccg.client.simple

import java.awt.Dimension
import javax.swing.JLabel

open class KLabel(name: String) : JLabel(name) {
    init {
        font = font.deriveFont(font.size * 2f)
        maximumSize = Dimension(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}