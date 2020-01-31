package com.example.socket.features

import TimeResponse
import com.example.socket.CrossWebSocket

class Time(_webSocket: CrossWebSocket) : BaseFeature<Time.TimeUpdateListener>(_webSocket) {

    fun requestTimeUpdate(timeUpdateListener: TimeUpdateListener) {
        addListener(timeUpdateListener)
    }

    override fun processMessage(mti: Int, message: String) {
        notifyListeners { it.onPriceUpdate(json.parse(TimeResponse.serializer(), message)) }
    }

    interface TimeUpdateListener {
        fun onPriceUpdate(timeResponse: TimeResponse)
    }
}
