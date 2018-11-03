package io.github.aedans.ccg.client.simple

import javax.swing.SwingUtilities
import javax.swing.UIManager

object MainMenu : KMenuFrame("CCG Simple Client", HostGameButton, JoinGameButton, ExitButton) {
    object HostGameButton : KButton("Host Game", {
        HostGameMenu.isVisible = true
        MainMenu.isVisible = false
    })

    object JoinGameButton : KButton("Join Game", {
        JoinGameMenu.isVisible = true
        MainMenu.isVisible = false
    })

    object ExitButton : KButton("Exit", { System.exit(0) })

    init {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        SwingUtilities.updateComponentTreeUI(this)
    }
}
