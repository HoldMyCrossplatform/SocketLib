package com.example.xplatformsocket

import android.util.Log
import com.example.socket.CrossWebSocket
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class SocketThing : CrossWebSocket() {

    private val client =
        object : WebSocketClient(URI("ws://frozen-tundra-68979.herokuapp.com/"), Draft_6455()) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d("Socket", "onOpen")
                message?.let {
                    sendMessage(it)
                    message = null
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d("Socket", "onClose(), code: $code, reason: $reason, remote: $remote")
            }

            override fun onMessage(message: String?) {
                Log.d("Socket", "onMessage: $message")

                if (message != null) {
                    this@SocketThing.onMessage(message)
                }
            }

            override fun onError(ex: Exception?) {
                Log.e("Socket", "onError: $ex")
            }
        }

    fun connect() {
        client.connect()
    }

    var message: String? = null

    override fun sendMessage(message: String) {
        if (!client.isOpen) {
            this.message = message
        } else {
            client.send(message)
        }
    }
}