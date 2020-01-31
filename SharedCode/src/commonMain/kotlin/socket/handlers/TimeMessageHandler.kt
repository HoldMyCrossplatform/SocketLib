package com.example.socket.handlers

//class TimeMessageHandler : BaseMessageHandler<TimeMessageHandler.OnTimeMessageListener>() {
//
//    override fun processMessage(mti: Int, message: String) {
//        val timeResponse = json.parse(TimeResponse.serializer(), message)
//
//        if (listener != null) {
//            listener!!.onTimeMessage(timeResponse.time)
//        }
//    }
//
//    interface OnTimeMessageListener {
//        fun onTimeMessage(time: String)
//    }
//
//}