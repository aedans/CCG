package io.github.aedans.ccg.client.simple

import javax.swing.JTextField

open class KTextField(text: String) : JTextField(text) {
    init {
        font = font.deriveFont(font.size * 2f)
    }
}
