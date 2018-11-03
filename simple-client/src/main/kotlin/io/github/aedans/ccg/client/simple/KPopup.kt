package io.github.aedans.ccg.client.simple

import javax.swing.WindowConstants

open class KPopup(name: String, text: String) : KMenuFrame(name, KLabel(text)) {
    init {
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
    }
}
