package io.github.aedans.ccg.client.simple

import java.awt.Component
import javax.swing.BoxLayout

open class KMenuFrame(title: String, vararg components: Component) : KFrame(title) {
    init {
        layout = BoxLayout(contentPane, BoxLayout.PAGE_AXIS)

        components.forEach { add(it) }

        pack()
    }

    final override fun pack() {
        super.pack()
    }
}