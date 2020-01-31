import com.example.model.request.PriceUpdateRequest
import com.example.socket.features.BaseFeature
import com.example.socket.CrossWebSocket
import model.response.PriceResponse

class Price(_webSocket: CrossWebSocket) : BaseFeature<Price.PriceUpdateListener>(_webSocket) {

    fun requestPriceUpdate(priceUpdateListener: PriceUpdateListener) {
        addListener(priceUpdateListener)
        webSocket.sendMessage(json.stringify(PriceUpdateRequest.serializer(), PriceUpdateRequest()))
    }

    override fun processMessage(mti: Int, message: String) {
        notifyListeners { it.onPriceUpdate(json.parse(PriceResponse.serializer(), message)) }
    }

    interface PriceUpdateListener {
        fun onPriceUpdate(priceResponse: PriceResponse)
    }
}
