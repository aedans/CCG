package io.github.aedans.ccg.client.simple

import java.awt.Dimension
import javax.swing.JTextField

open class KTextField(text: String) : JTextField(text) {
    init {
        font = font.deriveFont(font.size * 2f)
        preferredSize = Dimension(200, preferredSize.height)
    }
}
