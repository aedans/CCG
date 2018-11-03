package io.github.aedans.server.simple

import kotlinx.coroutines.*
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException

@UseExperimental(InternalCoroutinesApi::class)
object Server {
    fun host(port: Int): Deferred<Connection> = run {
        val serverSocket = ServerSocket(port)
        val async = GlobalScope.async {
            val socket = try {
                serverSocket.accept()
            } catch (e: SocketException) {
                throw CancellationException(e.message)
            }
            Connection(socket.getInputStream(), socket.getOutputStream())
        }
        async.invokeOnCompletion(onCancelling = true) {
            if (it != null) serverSocket.close()
        }
        async
    }

    fun join(host: String, port: Int): Deferred<Connection> = run {
        val async = GlobalScope.async {
            val socket = Socket(host, port)
            Connection(socket.getInputStream(), socket.getOutputStream())
        }
        async
    }
}
