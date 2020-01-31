package com.example.socket.handlers

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

abstract class BaseMessageHandler<T> {
    protected var listener: T? = null

    public fun addListener(listener: T) {
        this.listener = listener
    }

    protected val json = Json(
        JsonConfiguration(strictMode = false)
    )

    internal abstract fun processMessage(mti: Int, message: String)
}