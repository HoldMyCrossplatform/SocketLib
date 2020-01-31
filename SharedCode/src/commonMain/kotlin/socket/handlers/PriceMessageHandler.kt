package com.example.socket.handlers

//import model.response.PriceResponse
//
//class PriceMessageHandler : BaseMessageHandler<PriceMessageHandler.OnPriceMessageListener>() {
//
//    override fun processMessage(mti: Int, message: String) {
//        val timeResponse = json.parse(PriceResponse.serializer(), message)
//
//        if (listener != null) {
//            listener!!.onPriceUpdate(timeResponse)
//        }
//    }
//
//    interface OnPriceMessageListener {
//        fun onPriceUpdate(time: PriceResponse)
//    }
//}