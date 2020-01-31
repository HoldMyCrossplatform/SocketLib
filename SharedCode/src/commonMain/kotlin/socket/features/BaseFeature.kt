package com.example.socket.features

import com.example.socket.CrossWebSocket
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

abstract class BaseFeature<T>(val webSocket: CrossWebSocket) {
    private var listeners: ArrayList<T> = arrayListOf() //TODO arrayList vs mutableList?

    protected fun addListener(listener: T) {
        this.listeners.add(listener)
    }

    protected fun notifyListeners(block: (T) -> Unit){
        for(listener in listeners){
            block(listener)
        }
    }

    protected fun removeListener(listener: T){
        listeners.remove(listener)
    }

    protected val json = Json(
        JsonConfiguration(strictMode = false)
    )

    internal abstract fun processMessage(mti: Int, message: String)
}