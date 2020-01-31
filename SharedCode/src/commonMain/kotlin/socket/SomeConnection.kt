package com.example.socket

import Price
import com.example.model.response.BasicResponse
import com.example.socket.features.Time
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class SomeConnection(webSocket: CrossWebSocket) : CrossWebSocket.SocketMessageListener {

    init {
        webSocket.setListener(this)
    }

    val Time: Time = Time(webSocket)
    val Price: Price = Price(webSocket)

    private val mtiJson = Json(JsonConfiguration(strictMode = false))

    override fun onMessage(message: String) {
        val mti = getMti(message) ?: return

        when (mti) {
            0 -> {
                Time.processMessage(mti, message)
            }
            1 -> {
                Price.processMessage(mti, message)
            }
            else -> {
            }
        }
    }

    private fun getMti(message: String): Int? {
        return try {
            mtiJson.parse(BasicResponse.serializer(), message).mti
        } catch (e: Exception) {
            null
        }
    }
}