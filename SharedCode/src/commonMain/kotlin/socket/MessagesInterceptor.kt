package com.example.socket
//
//import com.example.model.response.BasicResponse
//import com.example.socket.handlers.PriceMessageHandler
//import com.example.socket.handlers.TimeMessageHandler
//import kotlinx.serialization.json.Json
//import kotlinx.serialization.json.JsonConfiguration
//
//class MessagesInterceptor {
//
//    val timeMessageHandler: TimeMessageHandler = TimeMessageHandler()
//    val priceMessageHandler: PriceMessageHandler = PriceMessageHandler()
//
//    private val mtiJson = Json(
//        JsonConfiguration(strictMode = false)
//    )
//
//    fun onMessage(message: String) {
//        val mti = getMti(message) ?: return
//
//        when (mti) {
//            0 -> {
//                timeMessageHandler.processMessage(mti, message)
//            }
//            1 -> {
//                priceMessageHandler.processMessage(mti, message)
//            }
//            else -> {
//            }
//        }
//    }
//
//    private fun getMti(message: String): Int? {
//        return try {
//            mtiJson.parse(BasicResponse.serializer(), message).mti
//        } catch (e: Exception) {
//            null
//        }
//    }
//}