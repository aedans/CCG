package io.github.aedans.ccg.client.simple

import java.awt.Component
import javax.swing.JPanel

open class KPanel : JPanel() {
    init {
        font = font.deriveFont(font.size * 2f)
    }

    fun addAll(components: List<Component>) {
        components.forEach { add(it) }
    }

    fun addAll(vararg component: Component) {
        component.forEach { add(it) }
    }
}
