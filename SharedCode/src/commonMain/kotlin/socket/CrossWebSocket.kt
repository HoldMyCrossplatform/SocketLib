package com.example.socket

abstract class CrossWebSocket {
    val messagesInterceptor = MessagesInterceptor()

    fun onMessage(message: String) {
        messagesInterceptor.onMessage(message)
    }

    abstract fun sendMessage(message: String)
}