package io.github.aedans.ccg.client.simple

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.Dimension
import javax.swing.JButton

open class KButton(name: String, action: suspend CoroutineScope.() -> Unit = { }) : JButton(name) {
    init {
        font = font.deriveFont(font.size * 2f)
        maximumSize = Dimension(Int.MAX_VALUE, Int.MAX_VALUE)

        addActionListener {
            GlobalScope.launch { action() }
        }
    }
}