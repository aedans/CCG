package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Player
import io.github.aedans.server.simple.Server
import kotlinx.coroutines.InternalCoroutinesApi

@UseExperimental(InternalCoroutinesApi::class)
class JoinGameMenu(private val mainMenu: MainMenu) : KMenuFrame("Join Game") {
    private val ipBox = KTextField("127.0.0.1")
    private val portBox = KTextField("8080")
    private val nameBox = KTextField("Player 2")
    private val submit = KButton("Submit") {
        val connection = Server.join(ipBox.text, portBox.text.toInt())
        val waitingFor = WaitingFor(connection)
        connection.invokeOnCompletion(onCancelling = true) {
            if (it != null) {
                isVisible = true
                KExceptionPopup(it)
            }
            waitingFor.isVisible = false
        }
        isVisible = false
        GameUI.start(Player(nameBox.text, emptyList(), emptyList(), emptyList()), connection.await())
    }

    private val cancel = KButton("Cancel") {
        mainMenu.isVisible = true
        isVisible = false
    }

    init {
        addAll(ipBox, portBox, nameBox, submit, cancel)
        pack()
    }
}
