package io.github.aedans.ccg

import io.github.aedans.ccg.client.simple.MainMenu
import javax.swing.UIManager

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        MainMenu()
    }
}
