package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Player
import io.github.aedans.server.simple.Server
import kotlinx.coroutines.InternalCoroutinesApi

@UseExperimental(InternalCoroutinesApi::class)
object JoinGameMenu : KMenuFrame("Join Game", IpBox, PortBox, NameBox, Submit, Cancel) {
    object IpBox : KTextField("127.0.0.1")
    object PortBox : KTextField("8080")
    object NameBox : KTextField("Player 2")
    object Submit : KButton("Submit", {
        val connection = Server.join(IpBox.text, PortBox.text.toInt())
        val waitingFor = WaitingFor(connection)
        connection.invokeOnCompletion(onCancelling = true) {
            if (it != null) {
                JoinGameMenu.isVisible = true
                KExceptionPopup(it)
            }
            waitingFor.isVisible = false
        }
        JoinGameMenu.isVisible = false
        GameUI.start(Player(NameBox.text, emptyList(), emptyList(), emptyList()), connection.await())
    })

    object Cancel : KButton("Cancel", {
        JoinGameMenu.isVisible = false
        MainMenu.isVisible = true
    })
}
