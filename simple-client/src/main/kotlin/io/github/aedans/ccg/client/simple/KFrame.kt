package io.github.aedans.ccg.client.simple

import java.awt.Dimension
import javax.swing.JFrame

open class KFrame(title: String) : JFrame() {
    init {
        this.title = title

        minimumSize = Dimension(320, 0)

        defaultCloseOperation = EXIT_ON_CLOSE

        isVisible = true
    }
}
