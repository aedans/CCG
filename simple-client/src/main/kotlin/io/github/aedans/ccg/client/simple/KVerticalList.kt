package io.github.aedans.ccg.client.simple

import javax.swing.BoxLayout

open class KVerticalList : KPanel() {
    init {
        @Suppress("LeakingThis")
        layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
    }
}
