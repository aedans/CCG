package io.github.aedans.ccg.client.simple

class MainMenu : KMenuFrame("CCG Simple Client") {
    private val hostGameButton =  KButton("Host Game") {
        HostGameMenu(this@MainMenu)
        isVisible = false
    }

    private val joinGameButton = KButton("Join Game") {
        JoinGameMenu(this@MainMenu)
        isVisible = false
    }

    private val decksButton = KButton("Decks") {
        DecksMenu(this@MainMenu)
        isVisible = false
    }

    private val exitButton = KButton("Exit") { System.exit(0) }

    init {
        addAll(hostGameButton, joinGameButton, decksButton, exitButton)
        pack()
    }
}
