package com.example.socket

abstract class CrossWebSocket {
    private var listener: SocketMessageListener? = null

    internal fun setListener(listener: SocketMessageListener){
        this.listener = listener
    }

    fun onMessage(message: String) {
        listener?.onMessage(message)
    }

    abstract fun sendMessage(message: String)

    interface SocketMessageListener{
        fun onMessage(message: String)
    }
}