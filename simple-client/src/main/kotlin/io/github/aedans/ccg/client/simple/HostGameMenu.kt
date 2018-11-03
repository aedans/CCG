package io.github.aedans.ccg.client.simple

import io.github.aedans.ccg.backend.Player
import io.github.aedans.server.simple.Server
import kotlinx.coroutines.InternalCoroutinesApi

@UseExperimental(InternalCoroutinesApi::class)
object HostGameMenu : KMenuFrame("Host Game", PortBox, NameBox, Submit, Cancel) {
    object PortBox : KTextField("8080")
    object NameBox : KTextField("Player 1")
    object Submit : KButton("Submit", {
        val connection = Server.host(PortBox.text.toInt())
        val waitingFor = WaitingFor(connection)
        connection.invokeOnCompletion(onCancelling = true) {
            if (it != null) HostGameMenu.isVisible = true
            waitingFor.isVisible = false
        }
        HostGameMenu.isVisible = false
        GameUI.start(Player(NameBox.text, emptyList(), emptyList(), emptyList()), connection.await())
    })

    object Cancel : KButton("Cancel", {
        HostGameMenu.isVisible = false
        MainMenu.isVisible = true
    })
}
