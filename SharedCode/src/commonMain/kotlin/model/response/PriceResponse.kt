package model.response

import kotlinx.serialization.Serializable

@Serializable
data class PriceResponse(
    val mti: Int,
    val message: String,
    val currencies: List<CurrencyPair>
){
    @Serializable
    data class CurrencyPair(
        val value: Float,
        val symbol: String
    )
}