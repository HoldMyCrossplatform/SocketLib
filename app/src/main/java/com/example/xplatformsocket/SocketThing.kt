package com.example.xplatformsocket

import android.util.Log
import com.example.socket.CrossWebSocket
import com.example.socket.MessagesInterceptor
import com.example.socket.handlers.PriceMessageHandler
import com.example.socket.handlers.TimeMessageHandler
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class SocketThing : CrossWebSocket() {

    val client =
        object : WebSocketClient(URI("ws://frozen-tundra-68979.herokuapp.com/"), Draft_6455()) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d("Socket", "onOpen")
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

    fun connect(){
        client.connect()
    }

    override fun sendMessage(message: String) {
        client.send(message)
    }

    fun requestTime(onTimeMessageListener: TimeMessageHandler.OnTimeMessageListener) {
        messagesInterceptor.timeMessageHandler.addListener(onTimeMessageListener)
    }

    fun requestPrice(onPriceListener: PriceMessageHandler.OnPriceMessageListener) {
        messagesInterceptor.priceMessageHandler.addListener(onPriceListener)
    }
}