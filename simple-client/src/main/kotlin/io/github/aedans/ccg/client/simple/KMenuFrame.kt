package io.github.aedans.ccg.client.simple

import java.awt.Component
import javax.swing.BoxLayout

open class KMenuFrame(title: String) : KFrame(title) {
    init {
        layout = BoxLayout(contentPane, BoxLayout.PAGE_AXIS)
    }

    fun addAll(components: List<Component>) {
        components.forEach { add(it) }
    }

    fun addAll(vararg component: Component) {
        component.forEach { add(it) }
    }
}