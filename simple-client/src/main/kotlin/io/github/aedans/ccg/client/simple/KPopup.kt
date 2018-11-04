package io.github.aedans.ccg.client.simple

import java.awt.Component
import javax.swing.WindowConstants

open class KPopup(name: String, text: String) : KMenuFrame(name) {
    init {
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE

        add(KLabel(text))
    }

    final override fun add(comp: Component): Component {
        return super.add(comp)
    }
}
